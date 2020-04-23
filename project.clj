(defproject urban-disco "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.0"]]
  :main ^:skip-aot urban-disco.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}}
  :plugins [[jonase/eastwood "0.3.11"]
            [lein-cljfmt "0.6.7"]
            [lein-kibit "0.1.8"]])
