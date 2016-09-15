;
;  (C) Copyright 2015, 2016  Pavel Tisnovsky
;
;  All rights reserved. This program and the accompanying materials
;  are made available under the terms of the Eclipse Public License v1.0
;  which accompanies this distribution, and is available at
;  http://www.eclipse.org/legal/epl-v10.html
;
;  Contributors:
;      Pavel Tisnovsky
;

(ns clj-jenkins-api.jenkins-api
    "Module with functions implementing Jenkins API.")

(require '[clojure.data.json :as json])
(require '[clj-http.client   :as http-client])

(defn log
    "Thread-safe logging to standard output."
    [& args]
    (.println System/out (apply str (interpose \space args))))

(defn encode-spaces
    "Encode spaces in URL into its codes."
    [^String url]
    (clojure.string/replace url " " "%20"))

(defn job-name->url
    "Transform job name (that can contain spaces) to the URL part."
    [^String jenkins-url ^String job-name]
    (str jenkins-url "job/" (encode-spaces job-name)))

(defn update-jenkins-url
    "Updates URL to Jenkins (API) by adding basic authorization string if provided."
    [job-config-url jenkins-basic-auth]
    (if (empty? jenkins-basic-auth)
        job-config-url
        (cond (.startsWith job-config-url "https://") (str "https://" jenkins-basic-auth "@" (subs job-config-url 8))
              (.startsWith job-config-url "http://")  (str "http://"  jenkins-basic-auth "@" (subs job-config-url 7))
              :else job-config-url)))

(defn get-command 
    "The reimplementation of HTTP GET command that can use JVM keystore for
     establishing secure connections."
    [url]
    (:body (http-client/get url {
                    :keystore "keystore"
                    :keystore-pass "changeit"
                    :trust-store "keystore"
                    :trust-store-pass "changeit"})))

(defn post-command
    "The reimplementation of HTTP POST command that can use JVM keystore for
     establishing secure connections and use basic auth if provided."
    [jenkins-url jenkins-basic-auth job-name command]
    (let [url (str (job-name->url (update-jenkins-url jenkins-url jenkins-basic-auth) job-name) "/" command)]
        (log "URL to use: " url)
        (http-client/post url {
                    :keystore "keystore"
                    :keystore-pass "changeit"
                    :trust-store "keystore"
                    :trust-store-pass "changeit"})))

(defn read-list-of-all-jobs
    "Read list of all jobs via Jenkins API."
    [jenkins-url job-list-part]
    (let [all-jobs-url (str jenkins-url job-list-part)]
        (log "Using the following URL to retrieve all Jenkins jobs: " all-jobs-url)
        (let [data (json/read-str (get-command all-jobs-url))]
            (if data
                (get data "jobs")
                nil))))

(defn read-job-results
    "Read content of given filename from the job artifact."
    [jenkins-url job-name]
    (let [url (str (job-name->url jenkins-url job-name) "/lastSuccessfulBuild/artifact/results.json")]
        (log "Using the following URL to retrieve job results: " url)
        (try
            (slurp url)
            (catch Exception e
                 (.printStackTrace e)
                 nil))))

(defn read-file-from-artifact
    [jenkins-url job-name file-name print-stack-trace?]
    (let [url (str (job-name->url jenkins-url job-name) "/lastSuccessfulBuild/artifact/" file-name)]
        (log "*Using the following URL to retrieve file from artifact: " url)
        (try
            (slurp url)
            (catch Exception e
                 (if print-stack-trace?
                     (.printStackTrace e))
                 nil))))

(defn ok-response-structure
    "Structure returned to the calling function when the Jenkins API fails."
    [job-name command include-jenkins-reply? jenkins-response]
    (if include-jenkins-reply?
        {:status   "ok"
         :jobName  job-name
         :command  command
         :jenkinsResponse jenkins-response}
        {:status   "ok"
         :jobName  job-name
         :command  command}))

(defn error-response-structure
    "Structure returned to the calling function when the Jenkins API succeded"
    [job-name command exception]
    {:status   "error"
     :jobName  job-name
     :command  command
     :message  (.getMessage exception)
    })

(defn job-related-command
    "Call any job-related command via Jenkins API."
    [jenkins-url jenkins-auth include-jenkins-reply? job-name command]
    (try
        (let [response (post-command jenkins-url jenkins-auth job-name command)]
            (ok-response-structure job-name command include-jenkins-reply? response))
        (catch Exception e
            (.printStackTrace e)
            (error-response-structure job-name command e))))

(defn start-job
    "Start the given job via Jenkins API."
    [jenkins-url jenkins-auth include-jenkins-reply? job-name]
    (job-related-command jenkins-url jenkins-auth include-jenkins-reply? job-name "build"))

(defn enable-job
    "Enable the given job via Jenkins API."
    [jenkins-url jenkins-auth include-jenkins-reply? job-name]
    (job-related-command jenkins-url jenkins-auth include-jenkins-reply? job-name "enable"))

(defn disable-job
    "Disable the given job via Jenkins API."
    [jenkins-url jenkins-auth include-jenkins-reply? job-name]
    (job-related-command jenkins-url jenkins-auth include-jenkins-reply? job-name "disable"))

(defn delete-job
    "Delete the given job via Jenkins API."
    [jenkins-url jenkins-auth include-jenkins-reply? job-name]
    (job-related-command jenkins-url jenkins-auth include-jenkins-reply? job-name "doDelete"))

(defn replace-placeholder
    "Replace selected placeholder in the template."
    [string placeholder-name value]
    (if value
        (clojure.string/replace string (str "<placeholder id=\"" placeholder-name "\" />") value)
        string))

(defn update-template
    "Update the XML template with job configuration."
    [template git-repo branch metadata credentials-id]
    (-> template
        (replace-placeholder "git-repo"                   git-repo)
        (replace-placeholder "git-branch"                 (str "*/" branch))
        (replace-placeholder "metadata-language"          (get metadata :language))
        (replace-placeholder "metadata-environment"       (get metadata :environment))
        (replace-placeholder "metadata-content-directory" (get metadata :content_directory))
        (replace-placeholder "metadata-content-type"      (get metadata :content_type))
        (replace-placeholder "credentials-id"             credentials-id)))

(defn log-operation
    [job-name git-repo branch operation metadata credentials-id]
    (log "***" operation "***")
    (log "job-name" job-name)
    (log "git-repo" git-repo)
    (log "branch"   branch)
    (log "metadata" metadata)
    (log "crendetials-id" credentials-id))

(defn send-configuration-xml-to-jenkins
    "Send the file containing job configuration to Jenkins via its REST API."
    [url config]
    (http-client/post url {
        :body             config
        :content-type     "application/xml"
        :keystore         "keystore"
        :keystore-pass    "changeit"
        :trust-store      "keystore"
        :trust-store-pass "changeit"}))

(defn get-template
    "Read the template from template directory. There are two templates, one for jobs with metadata and second for one without metadata."
    [metadata-directory metadata]
    (slurp (if metadata (str metadata-directory "/template_with_metadata.xml")
                        (str metadata-directory "/template.xml"))))

(defn create-job
    "Update the selected job. Following values must be specified - git-repo, branch, credentials-id, and metadata for template."
    [jenkins-url jenkins-auth include-jenkins-reply? job-name git-repo branch credentials-id metadata-directory metadata]
    (log-operation job-name git-repo branch "create_job" metadata credentials-id)
    (let [template (get-template metadata-directory metadata)
          config   (update-template template git-repo branch metadata credentials-id)
          url      (str (update-jenkins-url jenkins-url jenkins-auth) "createItem?name=" (encode-spaces job-name))]
          (log "URL to use: " url)
          (try
              (->> (send-configuration-xml-to-jenkins url config)
                   (ok-response-structure job-name "create_job" include-jenkins-reply?))
              (catch Exception e
                  (.printStackTrace e)
                  (error-response-structure job-name "create_job" e)))))

(defn update-job
    "Update the selected job. Following values can be changed - git-repo, branch, and credentials-id."
    [jenkins-url jenkins-auth include-jenkins-reply? job-name git-repo branch credentials-id metadata-directory metadata]
    (log-operation job-name git-repo branch "update_job" metadata credentials-id)
    (let [template (get-template metadata-directory metadata)
          config   (update-template template git-repo branch metadata credentials-id)
          url      (str (job-name->url (update-jenkins-url jenkins-url jenkins-auth) job-name) "/config.xml")]
          (log "URL to use: " url)
          (try
              (->> (send-configuration-xml-to-jenkins url config)
                   (ok-response-structure job-name "update_job" include-jenkins-reply?))
              (catch Exception e
                  (.printStackTrace e)
                  (error-response-structure job-name "update_job" e)))))

