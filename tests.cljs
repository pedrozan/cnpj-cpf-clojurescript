(ns cpf-cnpj.tests
  (:require [cljs.test :refer-macros [deftest is testing run-tests]]
            [src.cnpj :as cnpj]
            [src.cpf :as cpf]))

; cnpj tests
(def cnpj [1 1 4 4 4 7 7 7 0 0 0 1 6 1])
(def wrong-cnpj [1 1 4 4 4 7 7 8 0 0 0 1 6 1])
(def str-cnpj "11.444.777/0001-61")
(def wrong-str-cnpj "11.444.778/0001-61")

(def w1 [5 4 3 2 9 8 7 6 5 4 3 2])
(def w2 [6 5 4 3 2 9 8 7 6 5 4 3 2])

(deftest one-line-mul-works
  (testing "one-line-mul should perform matrix multiplication on 1 x N matrix"
           (is (= 6 (cnpj/one-line-mul [1 1 1] [2 2 2] 3)))))

(deftest get-intermed-result-for-first-digit
  (testing "The intermediary result of the first validator digit on the CNPJ validation is being calculated correctly"
           (is (= 5 (cnpj/get-intermed-result cnpj w1)))))

(deftest get-intermed-result-for-second-digit
  (testing "The intermediary result of the second validator digit on the CNPJ validation is being calculated correctly"
           (is (= 10 (cnpj/get-intermed-result cnpj w2)))))

(deftest sort-digit-works-with-one
  (testing "Must return value accordingly to rule"
           (is (= 0 (cnpj/sort-digit 1)))))

(deftest sort-digit-works-with-two
  (testing "Must return value accordingly to rule"
           (is (= 9 (cnpj/sort-digit 2)))))

(deftest cnpj-string-to-array-works
  (testing "Passing a string to cnpj-string-to-array should return a sequence"
           (is (coll? (cnpj/cnpj-string-to-array str-cnpj))))
  (testing "Passing a sequence to cnpj-string-to-array should return a sequence"
           (is (coll? (cnpj/cnpj-string-to-array cnpj)))))

(deftest cvalidate-first-digit
  (testing "Passing a valid CNPJ should return true"
           (is (= true (cnpj/validate-first-digit cnpj))))
  (testing "Passing an invalid CNPJ should return false"
           (is (= false (cnpj/validate-first-digit wrong-cnpj)))))

(deftest cvalidate-second-digit
  (testing "Passing a valid CNPJ should return true"
           (is (= true (cnpj/validate-second-digit cnpj))))
  (testing "Passing an invalid CNPJ should return false"
           (is (= false (cnpj/validate-second-digit wrong-cnpj)))))

(deftest validate-cnpj
  (testing "Passing a valid CNPJ as sequence, returns true"
           (is (= true (cnpj/validate-cnpj cnpj))))
  (testing "Passing an invalid CNPJ as sequence, returns false"
           (is (= false (cnpj/validate-cnpj wrong-cnpj))))
  (testing "Passing a valid CNPJ as string, returns true"
           (is (= true (cnpj/validate-cnpj str-cnpj))))
  (testing "Passing an invalid CNPJ as string, returns false"
           (is (= false (cnpj/validate-cnpj wrong-str-cnpj)))))

; CPF tests
(def cpf [5 2 9 9 8 2 2 4 7 2 5])
(def wrong-cpf [5 1 9 9 8 2 2 4 7 2 5])
(def str-cpf "529.982.247-25")
(def wrong-str-cpf "529.932.247-25")
(def eq-cpf [1 1 1 1 1 1 1 1 1 1 1])
(def str-eq-cpf "111.111.111-11")

(deftest one-element?-works
  (testing "Should return true when one element is passed"
           (is (= true (cpf/one-element? [1]))))
  (testing "Should return false when more then one is passed"
           (is (= false (cpf/one-element? [1 2])))))

(deftest do-the-mod-works
  (testing "Should calculate x*10 mod 11"
           (is (= 5 (cpf/do-the-mod 347)))))

(deftest cpf-sum-works
  (testing "Performs the sum of the values from CPF"
           (is (= 347 (cpf/cpf-sum cpf 11)))))

(deftest validate-first-digit-works
  (testing "Returns true if the CPF is valid"
           (is (= true (cpf/validate-first-digit cpf))))
  (testing "Returns false if the CPF is invalid"
           (is (= false (cpf/validate-first-digit wrong-cpf)))))

(deftest validate-second-digit-works
  (testing "Returns true if the CPF is valid"
           (is (= true (cpf/validate-second-digit cpf))))
  (testing "Returns false if the CPF is invalid"
           (is (= false (cpf/validate-second-digit wrong-cpf)))))

(deftest cpf-string-to-array-works
  (testing "Passing a string to cpf-string-to-array should return a sequence"
           (is (coll? (cpf/cpf-string-to-array str-cpf))))
  (testing "Passing a sequence to cpf-string-to-array should return a sequence"
           (is (coll? (cpf/cpf-string-to-array cpf)))))

(deftest all-equals?-works
  (testing "Should return true if all elements are equal"
           (is (= true (cpf/all-equals? eq-cpf)))))

(deftest validate-cpf-works
  (testing "Returns true on valid sequence CPF"
           (is (= true (cpf/validate-cpf cpf))))
  (testing "Returns false on invalid sequence CPF"
           (is (= false (cpf/validate-cpf wrong-cpf))))
  (testing "Return true on valid string CPF"
           (is (= true (cpf/validate-cpf str-cpf))))
  (testing "Returns false on invalid string CPF"
           (is (= false (cpf/validate-cpf wrong-str-cpf))))
  (testing "Returns false on all equal sequence CPF"
           (is (= false (cpf/validate-cpf eq-cpf))))
  (testing "Returns false on all equal string CPF"
           (is (= false (cpf/validate-cpf str-eq-cpf)))))

;run tests
(cljs.test/run-tests)