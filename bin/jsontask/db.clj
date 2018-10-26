(ns jsontask.db
(:require [clojure.java.jdbc :as j])
(require [clojure.data.json :as json])
(:gen-class :main true
    :name jsontask.db
    :methods [#^{:static true}[jsonparser [String] String]]))


(def mysql-db {:subprotocol "mysql"
               :subname "//localhost:3306/jsonparser"
               :user "sachin"
               :password "mysql123"})

(defn jsonparser []
;(println "Enter the file name")
;(def path(read-line))
;(println "Enter the cadidate name")
(def var(read-line))                                                                                       ;;taking i/p from user
(def json1(json/read-str (slurp "Candidate.json"):key-fn keyword))     ;;Reading first Json
(def json2(json/read-str (slurp "Caremanagers.json"):key-fn keyword))  ;;Reading second Json

(j/execute! mysql-db ["delete from candidate"])                                                            ;;delete the table content 'candidate' for every new run
(def getkeys1 (get-in json1[:Candidate :candidates]))
(doseq [i getkeys1]
(j/insert! mysql-db :candidate
 {:candidate (get-in i[:candidateid]) :provider (get-in i[:Providerid]) :org (get-in i[:Orgid])}))           ;;candidate table





(j/execute! mysql-db ["delete from caremanager"])               ;;delete the table content of 'caremanager' for every new run
 (def c(get-in json2 [:Caremanager :caremanagers]))
 (loop [x 0]
     (when ( < x (count c))
       (def d (get-in c[x] [:caremanagers :caraemanagerid])) 
       ;(println d)
       (def e (get-in d [:Providerid]))
       (def b (get-in d [:caraemanagerid]))
       ;(println b)
       (def l (get-in d [:Orgid]))
       (if ( > (count e) 2) 
           (doseq [m e] 
               (if ( > (count l) 1) 
               (doseq [z l]                 
               (j/insert! mysql-db :caremanager {:caremanager b :provider m :org z}))
               (j/insert! mysql-db :caremanager {:caremanager b :provider m :org l})))
               (j/insert! mysql-db :caremanager {:caremanager b :provider e :org l}))
               (recur (+ x 1))))
 
 (spit "C:/Users/sm062323/jsontask/src/temp/records.txt" "" :append false)
 (let [jsonobj1(get-in json1 [:Candidate :candidates])]
 (doseq [i jsonobj1]
 (def getcandidates (get-in i [:candidateid]))
 (spit "C:/Users/sm062323/jsontask/src/temp/records.txt" getcandidates :append true)
 (spit "C:/Users/sm062323/jsontask/src/temp/records.txt" "\n" :append true)))
 
 
 
      
 (def queryresult(j/query mysql-db 
        ["select caremanager from candidate natural join caremanager where candidate = ?" var]))        ;;Query to fecth the caremanagers 
                   (def querystring(apply str queryresult))
                   (def rmvcrm(clojure.string/replace querystring #"\{:caremanager" ""))
                   (def rmvbraces(clojure.string/replace rmvcrm #"\}" ""))
                   (def rmvqts(clojure.string/replace rmvbraces #"\"" ""))
                   (apply str rmvqts))
                   ;(println  var "belong to" rmvqts))
(jsonparser)
