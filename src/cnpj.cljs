(ns src.cnpj
  (:require [clojure.string :as str]))

(def w1 [5 4 3 2 9 8 7 6 5 4 3 2])
(def w2 [6 5 4 3 2 9 8 7 6 5 4 3 2])

(defn one-line-mul [a b size]
  "Matrix multiplication on 1 x N matrices"
  (loop [sum 0
         i 0]
    (if (= i size)
      sum
      (recur (+ (* (nth a i) (nth b i)) sum) (inc i)))))

(defn get-intermed-result [cnpj w]
  "Calculates the mod 11 of the multiplication between the exploded CPF and the weights matrix"
  (mod (one-line-mul cnpj w (count w)) 11))

(defn sort-digit [r]
  "Calculates the validating digit"
  (if (< r 2)
    0
    (- 11 r)))

(defn cnpj-string-to-array [cnpj]
  "Will convert a CNPJ in string format into a sequence"
  (if (string? cnpj)
    (map int (vec (str/replace cnpj #"[\.\-\/]" "")))
    cnpj))

(defn validate-first-digit [cnpj]
  "This will make the validation of the first digit"
  (if (= (nth cnpj 12) (sort-digit (get-intermed-result cnpj w1)))
    true
    false))

(defn validate-second-digit [cnpj]
  "This will make the validation of the second digit"
  (if (= (nth cnpj 13) (sort-digit (get-intermed-result cnpj w2)))
    true
    false))

(defn validate-cnpj [cnpj]
  "This is the main function that validates the CNPJ"
  (let [cnpj-array (cnpj-string-to-array cnpj)]
    (if (and (validate-first-digit cnpj-array)
             (validate-second-digit cnpj-array))
      true
      false)))
