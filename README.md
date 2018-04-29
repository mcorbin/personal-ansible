# My infrastructure

The Ansible configuration for my personal websites.

This repo also contains a (scope limited, buggy, not tested) dynamic inventory script for Cloudstack in `inventory/production`.

## Installing dependencies

pip install -r requirements.txt

## Getting roles

ansible-galaxy install -r requirements.yml -f -p roles
