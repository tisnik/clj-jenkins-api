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

(require '[clj-http.client   :as http-client])

(defn job-name->url
    "Transform job name (that can contain spaces) to the URL part."
    [jenkins-url job-name]
    (str jenkins-url "job/" (.replaceAll job-name " " "%20")))

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
        ;(println "URL to use: " url)
        (http-client/post url {
                    :keystore "keystore"
                    :keystore-pass "changeit"
                    :trust-store "keystore"
                    :trust-store-pass "changeit"})))


