(ns jsontask.jsonparser
  
(:require [clojure.java.jdbc :as j])
(require [clojure.data.json :as json])
(use clojure.java.io))

(def mysql-db {:subprotocol "mysql"
               :subname "//localhost:3306/clojure_test"
               :user "sachin"
               :password "mysql123"})

;(j/query TESTDB ["select * from guru"])

;(defn list-users []
;  (sql/with-connection TESTDB
;  (sql/with-query-results rows
;  ["select * from EMPLYOEE"]
;  (println rows))))
;(list-users)



(defn jsonparser1 []
(def json1(json/read-str (slurp "C:/Users/sm062323/Desktop/json_task/Candidate.json"):key-fn keyword))
;(def json2(json/read-str (slurp "C:/Users/sm062323/Desktop/json_task/Caremanagers.json"):key-fn keyword))
;(def json3(json/read-str (slurp "C:/Users/sm062323/Desktop/json_task/provider.json"):key-fn keyword))
;(def getkeys1 (map #(select-keys (get-in % [:Candidate :candidates]) [:candidateid]) json1))
(def getkeys1 (get-in json1[:Candidate :candidates]))
(doseq [i getkeys1]
(j/insert! mysql-db :customer
   {:cust_id (get-in i[:candidateid]) :cust_name (get-in i[:Providerid]) :cust_orgid (get-in i[:Orgid])}))
(def query(j/query mysql-db 
  ["select * from customer Where cust_id = 'X' AND cust_name = 'P1' AND cust_orgid = 'Clinic A'"]))
  (println query))
(jsonparser1)


;(doseq [i getkeys1]
;(j/insert! mysql-db :fruit
; ;  {:name (get-in i[:candidateid])}
;   {:appearence (get-in i[:Providerid])})))

;(println getkeys1)                                    ;;get the value from json file of key 'candidateid'
;(doseq [i getkeys1]
;(j/insert! mysql-db :fruit
; ;  {:name (get-in i[:candidateid])}
;   {:appearence (get-in i[:Providerid])}))))
;(println (get-in i[:candidateid]))))
