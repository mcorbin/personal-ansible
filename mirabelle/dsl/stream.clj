(streams
 (stream
  {:name :cabourotte :default true}
  (expired
     (info))
  (not-expired
    (where [:= :healthcheck "dns-mcorbin.fr"]
      (reaper))
    (where [:= :service "cabourotte-healthcheck"]
      (index [:host :healthcheck :service])
      (by [:healthcheck]
        (moving-event-window 10
          (coll-percentiles [0.5 0.75 0.98 0.99]
            (with {:service "cabourotte-healthcheck-percentile" :host nil}
              (publish! :healthchecks-percentiles)))))
      (by [:host :healthcheck]
        (changed :state "ok"
          (sformat "cabourotte-healthcheck-alert-%s" :service [:healthcheck]
            (index [:host :service])
            (info)
            (tap :cabourotte-pagerduty)
            (push-io! :pagerduty-client))))))))
