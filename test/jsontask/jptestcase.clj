(ns jsontask.jptestcase

(:require [jsontask.db :refer :all]))
  (use 'clojure.test)
  
  (deftest jptestcase
    (is(= " C1 C3" (jsonparser)))                                ;;for i/p "x"
    (is(= " C1 C3" (jsonparser)))                               ;;for i/p "y"
    (is(= " C1 C2" (jsonparser))))                               ;;for i/p "z"
    
(run-tests 'jsontask.jptestcase)