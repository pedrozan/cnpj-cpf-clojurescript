(ns src.cpf
  (:require [clojure.string :as str]))

(defn one-element? [coll]
  "Test if coll has only one element"
  (and (seq coll)
       (empty? (rest coll))))

(defn do-the-mod [sum]
  "Here we execute the mod used on the checks"
  (mod (* sum 10) 11))

(defn cpf-sum [cpf max]
  "As part of the validation a sum of all elements times a decreasing counter is made"
  (loop [sum 0
         i max]
    (if (= i 1)
      sum
      (recur (+ (* (nth cpf (- max i)) i) sum) (dec i)))))

(defn validate-first-digit [cpf]
  "This will make the validation of the first digit"
  (if (= (nth cpf 9) (do-the-mod (cpf-sum cpf 10)))
    true
    false))

(defn validate-second-digit [cpf]
  "This will make the validation of the second digit"
  (if (= (nth cpf 10) (do-the-mod (cpf-sum cpf 11)))
    true
    false))

(defn cpf-string-to-array [cpf]
  "Will convert a CPF in string format into an array (actually a list)"
  (if (string? cpf)
    (map int (vec (str/replace cpf #"[\.-]" "")))
    cpf))

(defn all-equals? [cpf]
  "Check if all CPF numbers are equal, if they are, they pass the tests but are not allowed"
  (if (one-element? (dedupe cpf))
    true
    false))

(defn validate-cpf [cpf]
  "This is the main function that validates the CPF"
  (let [cpf-string (cpf-string-to-array cpf)]
    (if (and (not (all-equals? cpf-string)) (and (validate-first-digit cpf-string)
                                                 (validate-second-digit cpf-string)))
      true
      false)))
