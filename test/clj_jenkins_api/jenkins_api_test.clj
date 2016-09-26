;
;  (C) Copyright 2015, 2016  Pavel Tisnovsky
;
;  All rights reserved. This program and the accompanying materials
;  are made available under the terms of the Eclipse Public License v1.0
;  which accompanies this distribution, and is available at
;  http://www.eclipse.org/legal/epl-v10.html
;
;  Contributors:
;      Pavel Tisnovsky
;

(ns clj-jenkins-api.jenkins-api-test
  (:require [clojure.test :refer :all]
            [clj-jenkins-api.jenkins-api :refer :all]))

;
; Common functions used by tests.
;

(defn callable?
    "Test if given function-name is bound to the real function."
    [function-name]
    (clojure.test/function? function-name))



;
; Test for function existence
;

(deftest test-log-existence
    "Check that the clj-jenkins-api.jenkins-api/log definition exists."
    (testing "if the clj-jenkins-api.jenkins-api/log definition exists."
        (is (callable? 'clj-jenkins-api.jenkins-api/log))))



(deftest test-encode-spaces-existence
    "Check that the clj-jenkins-api.jenkins-api/encode-spaces definition exists."
    (testing "if the clj-jenkins-api.jenkins-api/encode-spaces definition exists."
        (is (callable? 'clj-jenkins-api.jenkins-api/encode-spaces))))



(deftest test-get-command-existence
    "Check that the clj-jenkins-api.jenkins-api/get-command definition exists."
    (testing "if the clj-jenkins-api.jenkins-api/get-command definition exists."
        (is (callable? 'clj-jenkins-api.jenkins-api/get-command))))



(deftest test-post-command-existence
    "Check that the clj-jenkins-api.jenkins-api/post-command definition exists."
    (testing "if the clj-jenkins-api.jenkins-api/post-command definition exists."
        (is (callable? 'clj-jenkins-api.jenkins-api/post-command))))


(deftest test-job-name->url-existence
    "Check that the clj-jenkins-api.jenkins-api/job-name->url definition exists."
    (testing "if the clj-jenkins-api.jenkins-api/job-name->url definition exists."
        (is (callable? 'clj-jenkins-api.jenkins-api/job-name->url))))


(deftest test-update-jenkins-url-existence
    "Check that the clj-jenkins-api.jenkins-api/update-jenkins-url definition exists."
    (testing "if the clj-jenkins-api.jenkins-api/update-jenkins-url definition exists."
        (is (callable? 'clj-jenkins-api.jenkins-api/update-jenkins-url))))



(deftest test-read-list-of-all-jobs-existence
    "Check that the clj-jenkins-api.jenkins-api/read-list-of-all-jobs definition exists."
    (testing "if the clj-jenkins-api.jenkins-api/read-list-of-all-jobs definition exists."
        (is (callable? 'clj-jenkins-api.jenkins-api/read-list-of-all-jobs))))



(deftest test-read-job-results-existence
    "Check that the clj-jenkins-api.jenkins-api/read-job-results definition exists."
    (testing "if the clj-jenkins-api.jenkins-api/read-job-results definition exists."
        (is (callable? 'clj-jenkins-api.jenkins-api/read-job-results))))



(deftest test-read-file-from-artifact-existence
    "Check that the clj-jenkins-api.jenkins-api/read-file-from-artifact definition exists."
    (testing "if the clj-jenkins-api.jenkins-api/read-file-from-artifact definition exists."
        (is (callable? 'clj-jenkins-api.jenkins-api/read-file-from-artifact))))



(deftest test-ok-response-structure-existence
    "Check that the clj-jenkins-api.jenkins-api/ok-response-structure definition exists."
    (testing "if the clj-jenkins-api.jenkins-api/ok-response-structure definition exists."
        (is (callable? 'clj-jenkins-api.jenkins-api/ok-response-structure))))



(deftest test-error-response-structure-existence
    "Check that the clj-jenkins-api.jenkins-api/error-response-structure definition exists."
    (testing "if the clj-jenkins-api.jenkins-api/error-response-structure definition exists."
        (is (callable? 'clj-jenkins-api.jenkins-api/error-response-structure))))



(deftest test-job-related-command-existence
    "Check that the clj-jenkins-api.jenkins-api/job-related-command definition exists."
    (testing "if the clj-jenkins-api.jenkins-api/job-related-command definition exists."
        (is (callable? 'clj-jenkins-api.jenkins-api/job-related-command))))



(deftest test-enable-job-existence
    "Check that the clj-jenkins-api.jenkins-api/enable-job definition exists."
    (testing "if the clj-jenkins-api.jenkins-api/enable-job definition exists."
        (is (callable? 'clj-jenkins-api.jenkins-api/enable-job))))



(deftest test-disable-job-existence
    "Check that the clj-jenkins-api.jenkins-api/disable-job definition exists."
    (testing "if the clj-jenkins-api.jenkins-api/disable-job definition exists."
        (is (callable? 'clj-jenkins-api.jenkins-api/job-related-command))))



(deftest test-delete-job-existence
    "Check that the clj-jenkins-api.jenkins-api/delete-job definition exists."
    (testing "if the clj-jenkins-api.jenkins-api/delete-job definition exists."
        (is (callable? 'clj-jenkins-api.jenkins-api/delete-job))))



(deftest test-create-job-existence
    "Check that the clj-jenkins-api.jenkins-api/create-job definition exists."
    (testing "if the clj-jenkins-api.jenkins-api/create-job definition exists."
        (is (callable? 'clj-jenkins-api.jenkins-api/create-job))))



(deftest test-update-job-existence
    "Check that the clj-jenkins-api.jenkins-api/update-job definition exists."
    (testing "if the clj-jenkins-api.jenkins-api/update-job definition exists."
        (is (callable? 'clj-jenkins-api.jenkins-api/delete-job))))



(deftest test-replace-placeholder-existence
    "Check that the clj-jenkins-api.jenkins-api/replace-placeholder definition exists."
    (testing "if the clj-jenkins-api.jenkins-api/replace-placeholder definition exists."
        (is (callable? 'clj-jenkins-api.jenkins-api/replace-placeholder))))



(deftest test-update-template-existence
    "Check that the clj-jenkins-api.jenkins-api/update-template definition exists."
    (testing "if the clj-jenkins-api.jenkins-api/update-template definition exists."
        (is (callable? 'clj-jenkins-api.jenkins-api/update-template))))



(deftest test-log-operation-existence
    "Check that the clj-jenkins-api.jenkins-api/log-operation definition exists."
    (testing "if the clj-jenkins-api.jenkins-api/log-operation definition exists."
        (is (callable? 'clj-jenkins-api.jenkins-api/update-template))))



(deftest test-send-configuration-xml-to-jenkins-existence
    "Check that the clj-jenkins-api.jenkins-api/send-configuration-xml-to-jenkins definition exists."
    (testing "if the clj-jenkins-api.jenkins-api/send-configuration-xml-to-jenkins definition exists."
        (is (callable? 'clj-jenkins-api.jenkins-api/update-template))))



(deftest test-get-template-existence
    "Check that the clj-jenkins-api.jenkins-api/get-template definition exists."
    (testing "if the clj-jenkins-api.jenkins-api/get-template definition exists."
        (is (callable? 'clj-jenkins-api.jenkins-api/update-template))))



;
; Function behaviours
;

(deftest test-encode-spaces
    "Check the clj-jenkins-api.jenkins-api/encode-spaces function"
    (testing "the clj-jenkins-api.jenkins-api/encode-spaces function"
        (are [x y] (= x y)
            ""            (encode-spaces "")
            "%20"         (encode-spaces " ")
            "x%20"        (encode-spaces "x ")
            "%20y"        (encode-spaces " y")
            "x%20y"       (encode-spaces "x y")
            "x%20y%20z"   (encode-spaces "x y z")
            "x%20%20%20z" (encode-spaces "x   z"))))

(deftest test-encode-spaces-NPE
    "Check the function the clj-jenkins-api.jenkins-api/encode-spaces"
    (testing "the function clj-jenkins-api.jenkins-api/encode-spaces"
        (is (thrown? NullPointerException (encode-spaces nil)))))

(deftest test-job-name->url-1
    "Check the clj-jenkins-api.jenkins-api/job-name->url"
    (testing "the clj-jenkins-api.jenkins-api/job-name->url"
        (are [x y] (= x y)
            "job/"                  (job-name->url "" "")
            " job/"                 (job-name->url " " "")
            "jenkins-url/job/"      (job-name->url "jenkins-url/" "")
            "jenkins-url:8080/job/" (job-name->url "jenkins-url:8080/" ""))))

(deftest test-job-name->url-2
    "Check the clj-jenkins-api.jenkins-api/job-name->url"
    (testing "the clj-jenkins-api.jenkins-api/job-name->url"
        (are [x y] (= x y)
            "jenkins-url:8080/job/"                  (job-name->url "jenkins-url:8080/" "")
            "jenkins-url:8080/job/job-name"          (job-name->url "jenkins-url:8080/" "job-name")
            "jenkins-url:8080/job/job%20name"        (job-name->url "jenkins-url:8080/" "job name")
            "jenkins-url:8080/job/long%20job%20name" (job-name->url "jenkins-url:8080/" "long job name"))))

(deftest test-job-name->url-not-NPE
    "Check the clj-jenkins-api.jenkins-api/job-name->url"
    (testing "the clj-jenkins-api.jenkins-api/job-name->url"
        (are [x y] (= x y)
            "job/"  (job-name->url ""  "")
            "job/"  (job-name->url nil ""))))

(deftest test-job-name->url-NPE
    "Check the clj-jenkins-api.jenkins-api/job-name->url"
    (testing "the clj-jenkins-api.jenkins-api/job-name->url"
        (is (thrown? NullPointerException (job-name->url ""  nil)))
        (is (thrown? NullPointerException (job-name->url nil nil)))))

