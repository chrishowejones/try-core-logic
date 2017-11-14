(ns try-core-logic.core
  (:require [clojure.core.logic :refer :all]))


(run* [q]
  (== q true))
;; => (true)

(run* [q]
  (membero q [1 2 3])
  (membero q [2 3 4]))
;; => (2 3)

;; unify with ==
(run* [q]
  (== q 1))
;; => (1)

(run* [q]
  (== 1 q))
;; => (1)

(run* [q]
  (== q {:a 1 :b 2}))
;; => ({:a 1, :b 2})

(run* [q]
  (== {:a q :b 2} {:a 1 :b 2}))
;; => (1)

;; not or;ing 1 2 3 but treating the list (1 2 3) as a list element so evals to
;; ((1 2 3))
(run* [q]
  (== q '(1 2 3)))
;; => ((1 2 3))

;; Constrain a to be intersection of lists
;; 1,2,3 and 3,4,5
(run* [q]
  (fresh [a]
    (membero a [1 2 3])
    (membero a [3 4 5])
    (== a q)))
;; => (3)

(run* [q]
  (fresh [a]
    (membero a [3 4 5])
    (== a q)
    (membero a [1 2 3])))
;; => (3)

(run* [q]
  (fresh [a]
    (== a q)
    (membero a [3 4 5])
    (membero a [1 2 3])))
;; => (3)

;; conde is alogical disjunct in other words
;; conde is an OR
(run* [q]
  (conde
   [succeed]))
;; => (_0)

(run* [q]
  (conde
   [succeed succeed succeed succeed]))
;; => (_0)

(run* [q]
  (conde
   [succeed succeed fail succeed]))
;; => ()

(run* [q]
  (conde
   [succeed]
   [succeed]))
;; => (_0 _0)

(run* [q]
  (conde
   [succeed]
   [fail]))
;; => (_0)

(run* [q]
  (conde
   [succeed (== q 1)]))

;; More Goals - numbero is a goal
;;
;; Conso:
;;
;; (conso x r s)
;;
;; The above expression only succeeds if s is a list with head x and rest r.

(run* [q]
  (conso 1 [2 3] q))
;; => ((1 2 3))

(run* [q]
  (conso 1 q [1 2 3]))
;; => ((2 3))
;; q is constrained to be a list which, when 1 is added as the head results in the list (1 2 3)

(run* [q]
  (conso q [2 3] [1 2 3]))
;; => (1)

;; resto is the complement of conso
;;
;; (resto l r)
;; constrains logic variable such that r is (rest l)

(run* [q]
  (resto [1 2 3 4] q))
;; => ((2 3 4))

;; Membero
;;
;; (membero x l)
;; constrains logic variables such that x is an element of l.

(run* [q]
  (membero q [1 2 3]))
;; => (1 2 3)

(run* [q]
  (membero 7 [1 3 8 q]))
