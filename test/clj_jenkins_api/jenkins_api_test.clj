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

;
; Function behaviours
;

(deftest test-encode-spaces
    "Check that the clj-jenkins-api.jenkins-api/encode-spaces definition exists."
    (testing "if the clj-jenkins-api.jenkins-api/encode-spaces definition exists."
        (are [x y] (= x y)
            ""            (encode-spaces "")
            "%20"         (encode-spaces " ")
            "x%20"        (encode-spaces "x ")
            "%20y"        (encode-spaces " y")
            "x%20y"       (encode-spaces "x y")
            "x%20y%20z"   (encode-spaces "x y z")
            "x%20%20%20z" (encode-spaces "x   z"))))

(deftest test-encode-spaces-NPE
    "Check that the clj-jenkins-api.jenkins-api/encode-spaces definition exists."
    (testing "if the clj-jenkins-api.jenkins-api/encode-spaces definition exists."
        (is (thrown? NullPointerException (encode-spaces nil)))))

