{:cabourotte
 {:default true,
  :actions
  {:action :sdo,
   :children
   ({:action :not-expired,
     :children
     ({:action :where,
       :params [[:= :healthcheck "dns-mcorbin.fr"]],
       :children ({:action :reaper, :params [:nil], :children []})}
      {:action :where,
       :params [[:= :service "cabourotte-healthcheck"]],
       :children
       ({:action :index, :params [[:host :healthcheck :service]]}
        {:action :by,
         :params [[:healthcheck]],
         :children
         ({:action :moving-event-window,
           :params [10],
           :children
           ({:action :coll-percentiles,
             :params [[0.5 0.75 0.98 0.99]],
             :children
             ({:action :sflatten,
               :children
               ({:action :with,
                 :children
                 ({:action :tap, :params [:cabourotte-percentiles]}
                  {:action :index,
                   :params [[:service :healthcheck :quantile]]}),
                 :params
                 [{:service "cabourotte-healthcheck-percentile",
                   :host nil}]})})})})}
        {:action :by,
         :params [[:host :healthcheck]],
         :children
         ({:action :moving-event-window,
           :params [10],
           :children
           ({:action :coll-percentiles,
             :params [[0.5 0.75 0.98 0.99]],
             :children
             ({:action :sflatten,
               :children
               ({:action :with,
                 :children
                 ({:action :tap, :params [:cabourotte-percentiles]}
                  {:action :index,
                   :params [[:service :healthcheck :quantile]]}),
                 :params
                 [{:service
                   "cabourotte-healthcheck-percentile"}]})})})}
          {:action :changed,
           :params [:state "ok"],
           :children
           ({:action :sformat,
             :params
             ["cabourotte-healthcheck-alert-%s"
              :service
              [:healthcheck]],
             :children
             ({:action :index, :params [[:host :service]]}
              {:action :tap, :params [:cabourotte-pagerduty]}
              {:action :push-io!, :params [:pagerduty]})})})})})})}}}
