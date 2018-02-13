(ns medschema.physiology.cytokines
  (require clojure.spec.alpha :as s))

;; Trying to flesh out (pun intended) immune responses and cytokines among other things.
;; This is also one approach to educate myself (
(def cytokines
  {:IL_10 {:name "Interleukin 10"
           :gene "IL10"
           :class 2
           :function ""
           :created_by
           :increases []
           :decreases ["Japanese Knotwood"]
           :actions {:downregulates []}
           :resources ["https://en.wikipedia.org/wiki/Interleukin_10"]}})