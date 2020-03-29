# medschema

A Clojure library designed to document schema(s) for **anonymous** medical data to facilitate collection
and analysis of medical data. There is enormous potential to share and analyze all medical data to help
properly diagnose, to educate the patient and doctors at a significantly reduced cost.

Many of the studies, guidelines, and literature for symptoms, diagnosis and treatment are only based on
an exclusive set of test data that effectively ignores real world usage. **All medical visits, symptoms, and 
data are valuable** and represent real world, practical information that can be used to identify averages, deviations, 
and outliers. A common high level data structure makes it easy to share and analyze. The Patient owns the medical 
data but may easily anonymously share it for diagnosis, analysis, and verification purposes.

### Goals
* develop a common high level data schema and increase sharing of data
* identify patterns in data to improve diagnosis (ML, DeepMind, [clash](https://github.com/dmillett/clash), etc)
* **educate** doctors and patients
* reduce cost and duration for accurate diagnosis
* Patient completely owns immutable medical data
* **Patient crypto-currency/blockchain contracts that provide incentives to share (anonymously) data for research**

### Challenges
* Existing insurance political structure/inertia
* Multiple lab formats
* Lack of standard tests for cytokines, chemokines, etc
* Easy input of data into schema
* Adapting existing data

### Data Attributes
There are many different categories that should be tested at regular intervals in different labs to build a
more robust model of our physiology. For example, the human immune system is very dynamic and yet ignored by
the majority fo clinical doctors and rarely submitted for testing in proximity to illnesses, vaccinations, 
acupuncture, etc. 

* **time and date**
* **location** (hospital/clinic, zip_code)
* **physical** (age, height, weight, gender, blood pressure, temperature, etc) 
* **symptoms** (verbal, observable, measurable)
* **genetic expression**
* **vitamin, mineral levels**
* **specific markers** (cytokines, chemokines, interferons red blood cells, neutrophils, etc)
* **natural molecules** (detection lab, method)
* **man-made molecules**  (detection lab, method)
* **lab tests** (test-name, lab-name, date, process, dr/tech, etc)
* **diagnosis** (patient, doctors, etc)
* **treatment** (former, current, suggested)

* *Items like verbal symptoms are easily attainable from any mobile device using voice-to-text translations.*

## Usage

A sample data schema of how doctor visits and measurements can be combined with lab test results, symptom
descriptions, and diagnosis. Further refinement is required (todo)

For efficiency and longer term data compatibility, there should be fewer top level schema fields that remain unchanged
with nested data structures that are more flexible (over time). The primary data types will probably be:

* Dictionary/HashMap
* Arrays/Lists
* Fields

There are thousands of health labs around the Earth, so this library will facilitate the adaptation of individual lab schemas
when appropriate.

### Sample High Level Schema

A secondary goal is to establish a glossary of common/reference terms and definitions and refer to them in the 
data structures. 

```json
{"identity":"foo123", "blockchain":{"id":"", "contract":""}, "location":{"postal-code": "", "type": "home"}, 
 "date_time":"2020-03-28T17:07:31.000", "age":42, "gender":"", 
 "data":{"temperature":{"device":"braunXyz" , "value":98.4}, "pulse":{"device":"hand", "value":68}, 
         "oxygen":{"device":"oxi", "value":99}, "weight": {}, 
         "descriptions":{"head":"", "sinus":"", "throat":"", "eyes":"", "stomach":"", "intestines":"", "lungs":"",
                         "heart":"", "arms":"", "hands":"", "legs":"", "feet":"", "urine":"", "bowels":"", "skin":"",
                         "back":"" ,"teeth":"", "tongue":"", "energy":"", "general":""},
         "diet":{"time":[{"food":{}, "supplements":{}, "herbals":{}}]}, "exercise": {}, 

 "observation":["Lucid", "Calm", "Tired", "Rational", "No tick bites", "Upstate New York Autumn Camping Vacation"],
 "labs": {"genetic": {"23andMe": {}},
          "immune_markers":{"CD57":null, "cytokines":{"IL1B":null, "IL2":null, "IL6":null, "IL10":null, "TNF-a": null}, "chemokines":{}}, 
          "natural":{"vitamin_D":{"value":41, "units":"ng/mL", "type":"25-Hydroxy", "laboratory":{"name":"some lab", "code":"11233"}}}, 
          "artificial":{"C13H18O2": null},
          "influenza": {"device":"abottxyz", "value": true}}},
 "diagnosis":{"influenza":{"doctor":"Strainge:-)", "probability": 90.0}, 
             "Arthritis": {"doctor":"Strainge", "probability":48.0}, 
             "Lyme":{"doctor":"Strainge", "probability":10.0}},
 "treatment":{"existing":{}, 
              "recommended":{"diet":{}, "exercise":{}, "supplements":{},"herbals":{}, "prescriptions":{}, "therapies":{}}
             }}
```
A multi-row sample with an analytical question

```clojure
;; Imagine asking questions of 1+ million rows of data with multiple blood test readings. 
;; ? What patterns exist for Vitamin D for Men in northern geographic regions (postal code)?

; An example using dmillett/clash to determine value frequencies for specific data elements
; This would typically take around 1 second for a million rows of data like this
(def mvdn (mv-freqs data :kpsets [{:kp [:physical] :ks [:gender :age]} ; value frequencies for :gender, :age
                                  {:kp [:labs :natural] :ks [:vitamin_D]} ; value frequencies for :vitamin_D
                                  {:kp [:location] :ks [:postal_code]}] ; value frequencies for postal_code 
                          :plevel 2))

; Perhaps form a study looking improving Vitamin D levels for a subset of postal_codes  and older men?
[
{:identify "a1" :datetime "2017-11-14T22:15:41+00:00" :location {:postal_code "12345"} 
 :physical {:age 42 :gender "M" :weight 75.4 kg :temperature 96.2 :blood_pressure [:pressure 110 :heart_rate 72]}
 :labs {:natural {:vitamin_D {:value 41 :units "ng/mL" :type "25-Hydroxy" :laboratory "some lab 1" :lab_code "11233"}}}}
{:identify "a2" :datetime "2017-11-10T12:15:41+00:00" :location {:postal_code "78811"} 
 :physical {:age 26 :gender "F" :weight 45.5 kg :temperature 98.2 :blood_pressure [:pressure 105 :heart_rate 70]}
 :labs {:natural {:vitamin_D {:value 47 :units "ng/mL" :type "25-Hydroxy" :laboratory "some lab 2" :lab_code "123456"}}}}
{:identify "a3" :datetime "2017-11-13T10:15:41+00:00" :location {:postal_code "65477"} 
 :physical {:age 51 :gender "M" :weight 90.1 kg :temperature 96.2 :blood_pressure [:pressure 140 :heart_rate 80]}
 :labs {:natural {:vitamin_D {:value 28 :units "ng/mL" :type "25-Hydroxy" :laboratory "some lab" :lab_code "456722"}}}}
]
```

### Sample Lab Schema

Lab reports can vary by company, test, date, process, etc. It's important capture this meta data in addition to the
result to help compare procedures by date for modernization and by quality.
 
* Establish common test names to share across laboratories
* Lab test name IDs are flexible and up to the lab 
* Establish adaptors for lab test catalogs

```clojure
; Lab fields can vary by test
{:name "Vitamin D" 
 :description "A test with good sensitivity and specificity"
 :aliases ["Test a", "Test b", "Test c"]
 :laboratory {:name "some lab" :address {} :contact_info {:phone "111-222-3333" :email "test@somelab.com"}}
 :lab_code 11233
 :lab_techs ["Dr Foo Bar", "Dr Zoo"]
 :test_date "2017-11-3T20:00:00+00:00"
 ; static lab reference data
 :requirements {:preferred_specimen "1.0 mL serum"
                :container "SST (gold)"
                :alternate_container "Lithium Heparin"
                :minimum_volume "0.3 mL serum or plasma"
                :repeat_testing false
                :collection ["clot", "centrifuge 15 minutes" "transfer into tube <= 2 hours for non-gel tubes"]
                :transport_temperature "refrigerated"
                :stability {:room_temp "8 hours" :refrigerated "4 days" :frozen "6 months"}
                :rejection_criteria "Gross hemolysis"
                :method "... binding assay"
                :ranges {:deficient "< 21 ng/ml" 
                         :low "22 - 30 ng/ml" 
                         :low_normal "31 - 44 ng/ml" 
                         :optimum "45 - 60 ng/ml" 
                         :high_normal "61 - 74 ng/ml"
                         :high "> 75 ng/ml"}
                :significance ["From sunlight" "Supplements D3, D2 as 25-hydroxy vitamin D", "etc"]         
               } 
 :results {:final {:value 43 :units "ng/ml"} :initial {:value 43 :units "ng/ml"} :interpretation {}}
 :notes []
}
```

<a name="data_primer"></a>
## Data Primer

```clojure
; HashMap or Dictionary structures have unique keys that point to a specific value
; keys cannot be used more than once, but values can repeat, order does not matter
; Fast for singular lookups
{ unique_key1  any_value, unique_key2 any_value}   

; Arrays are lists of values where order is maintained, but uniqueness does not matter
; Good for storing like or grouped items
[3 4 5 6 6 3 17 21 3]
```

<a name="License"></a>

## License

Copyright © 2017 dmillett

This program and the accompanying materials are made available under the
terms of the GNU LESSER GENERAL PUBLIC LICENSE Version 2.1
