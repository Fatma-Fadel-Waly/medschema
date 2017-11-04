# medschema

A Clojure library designed to document schema(s) for **anonymous** medical data to facilitate collection
and analysis of medical data. There is enormous potential to share and analyze all medical data to help
properly diagnose, to educate the patient and doctors at a significantly reduced cost.

Many of the studies, guidelines, and literature for symptoms, diagnosis and treatment are based soley on
and exclusive set of test data that effectively ignores real world usage. All medical visits, symptoms, and data is valuable and represent real world, practical information that
can be used to identify averages, deviations, and outliers. A common high level data structure makes it easy to share
and analyze. The Patient owns the medical data but may easily anonymously share it for diagnosis, analysis, and 
verification purposes.

**Goals**
* develop a common high level data schema and increase sharing of data
* identify patterns in data to improve diagnosis (ML, DeepMind, [clash](https://github.com/dmillett/clash), etc)
* educate doctors and patients
* reduce cost and duration for accurate diagnosis

**Challenges**

* Multiple lab formats
* Easy input of data into schema
* Adapting existing data

**Data Attributes**

* **time and date**
* **location** (hospital/clinic, zip_code)
* **physical** (age, height, weight, gender, blood pressure, temperature, etc) 
* **symptoms** (verbal, observable, measurable)
* **genetic expression**
* **vitamin, mineral levels**
* **specific markers** (cytokines, red blood cells, ferritin, etc)
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

### Sample High Level Schema

A secondary goal is to establish a glossary of common/reference terms and definitions and refer to them in the 
data structures. 

```clojure
{:identity {:hash "sdfasdreaf13e"} ; anonymous identifier served via API
 :datetime "" ; iso-8601 utc :-)
 :location {:postal_code 60606 }  ; Hospital, clinic, etc
 :physical {:age 42 :gender "M" :weight 75.4 kg :temperature 96.2 :blood_pressure [110 72]}
 :patient {:symptoms ["fever", "nausea", "tire"] :recorded_symptoms {:free_app  :alexa nil, :siri nil,}}
 :observation ["Lucid", "Calm", "Tired", "Rational", "No tick bites", "Upstate New York Autumn Camping Vacation"]
 :blood {:genes {:23andMe {}}   ; todo: ?Naming conventions for gene expression by lab?
         :markers {:CD57 nil}   ;
         :natural {:vitaminD nil} 
         :artificial {:C13H18O2 nil}}
 :diagnosis {"influenza" {:doctor "Strainge:-)" :statistics 90.0} 
             "Lyme" {}}
 :treatment {:diet {} 
             :exercise {} 
             :supplements {} 
             :prescriptions {} 
             :therapies {}}            
}
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
 :requirements {}  ; static lab reference data
 :results {:actual {} :interpretation {}}
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

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
