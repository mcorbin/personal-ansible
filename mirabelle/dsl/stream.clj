(streams
 (stream
  {:name :cabourotte :default true}
  (not-expired
    (where [:= :healthcheck "dns-mcorbin.fr"]
      (reaper))
    (where [:= :service "cabourotte-healthcheck"]
      (index [:host :healthcheck :service])
      (by [:healthcheck]
        (moving-event-window 10
          (coll-percentiles [0.5 0.75 0.98 0.99]
            (sflatten
              (with {:service "cabourotte-healthcheck-percentile" :host nil}
                (tap :cabourotte-percentiles)
                (index [:service :healthcheck :quantile]))))))
      (by [:host :healthcheck]
        (moving-event-window 10
          (coll-percentiles [0.5 0.75 0.98 0.99]
            (sflatten
              (with {:service "cabourotte-healthcheck-percentile"}
                (tap :cabourotte-percentiles)
                (index [:service :healthcheck :quantile])))))
        (changed :state "ok"
          (sformat "cabourotte-healthcheck-alert-%s" :service [:healthcheck]
            (index [:host :service])
            (tap :cabourotte-pagerduty)
            (push-io! :pagerduty))))))))
