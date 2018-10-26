(ns jsontask.jsonparserorg
(:require [clojure.java.jdbc :as j])
(require [clojure.data.json :as json])
(use clojure.java.io)
 (:gen-class :main true
    :name jsontask.jsonparserorg
    :methods [#^{:static true}[filepath [String] String]
               #^{:static true}[jsonparser [String] String]]))
         


(def mysql-db {:subprotocol "mysql"
               :subname "//localhost:3306/jsonparser"                                              ;;database name
               :user "sachin"                                                                      ;;credenteials that has access to the database jsonparser
               :password "mysql123"})

(defn filepath []
(def path(read-line))                                                                                        ;;taking i/p from user
(def json1(json/read-str (slurp "C:/Users/HP/Desktop/json_task/Candidate.json"):key-fn keyword))         ;;Reading first Json
(def json2(json/read-str (slurp "C:/Users/HP/Desktop/json_task/Caremanagers.json"):key-fn keyword))   ;;Reading second Json

(j/execute! mysql-db ["delete from candidate"])                                                             ;;delete the table content 'candidate' for every new run
(def getkeys1 (get-in json1[:Candidate :candidates]))
(doseq [i getkeys1]
(j/insert! mysql-db :candidate
 {:candidate (get-in i[:candidateid]) :provider (get-in i[:Providerid]) :org (get-in i[:Orgid])}))          ;;candidate table
 

(j/execute! mysql-db ["delete from caremanager"])                                                  ;;delete the table content of 'caremanager' for every new run
 (def jsonobjarray(get-in json2 [:Caremanager :caremanagers]))
 (loop [x 0]
     (when ( < x (count jsonobjarray))
       (def jobjindex (get-in jsonobjarray[x] [:caremanagers :caraemanagerid]))                     ;;get the nested json object index c[0],c[1],c[2]
       (def parr (get-in jobjindex [:Providerid]))
       (def carr (get-in jobjindex [:caraemanagerid]))
       (def oarr (get-in jobjindex [:Orgid]))
       (if ( > (count parr) 2) 
           (doseq [m parr] 
               (if ( > (count oarr) 1) 
               (doseq [z oarr]                 
               (j/insert! mysql-db :caremanager {:caremanager carr :provider m :org z}))
               (j/insert! mysql-db :caremanager {:caremanager carr :provider m :org oarr})))
               (j/insert! mysql-db :caremanager {:caremanager carr :provider parr :org oarr}))
               (recur (inc x))))
 
 (spit "C:/Users/sm062323/jsontask/src/temp/records.txt" "" :append false)
 (let [jsonobj1(get-in json1 [:Candidate :candidates])]
 (doseq [i jsonobj1]
 (def getcandidates (get-in i [:candidateid]))
 (spit "C:/Users/sm062323/jsontask/src/temp/records.txt" getcandidates :append true)
 (spit "C:/Users/sm062323/jsontask/src/temp/records.txt" "\n" :append true))))


(filepath)
 
 
 (defn jsonparser [path]
 (def queryresult(j/query mysql-db 
        ["select caremanager from candidate natural join caremanager where candidate = ?" var]))  ;;Query to fecth the caremanagers on the given candidate name
  ;(println var "belong to" queryresult)
 (def querystring(apply str queryresult))
 (def rmvcrm(clojure.string/replace querystring #"\{:caremanager" ""))
 (def rmvbraces(clojure.string/replace rmvcrm #"\}" ""))
 (def rmvqts(clojure.string/replace rmvbraces #"\"" ""))
 rmvqts)
 

(jsonparser)


(defn -main[]
  )