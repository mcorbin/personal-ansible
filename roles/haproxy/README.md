# HAProxy Ansible role

A simple and idempotent HAProxy role for Debian 8 and 9. You can use it to install the latest HAProxy release.

## Installation

This role can install the latest haproxy releases from https://haproxy.debian.net/.

Important variables are (see `defaults.yml` for the default values):

- `haproxy_version`: The HAProxy version
- `haproxy_force_install`: Force HAProxy install (apt -f)
- `haproxy_debian_distribution`: The Debian distribution
- `haproxy_package`: This variable is used by apt to install HAProxy (default `haproxy={{ haproxy_version }}.*`)
- `haproxy_debian_backports_enabled`: Enable Debian backports
- `haproxy_debian_backports`: The Debian backports URL
- `haproxy_haproxy_backports`: The HAProxy backport URL
- `haproxy_haproxy_backports_enabled`: Enable haproxy.debian.net backports

## Configuration

By default, this role deploy the default HAProxy configuration.

- `haproxy_configuration_templates`: A list of map, each map having a `src` and `dest` key.

This role will create directory at `/etc/haproxy/haproxy.d`, and template the templates referenced in `haproxy_templates`. Then, it will assemble all the files present in `/etc/haproxy/haproxy.d` (in alphabetical order) into `/etc/haproxy/haproxy.cfg`,

This allows you to split your HAProxy configuration in multiple Ansible templates for better maintainability.

Of course, everything is idempotent ;)

The best thing to do is to define your HAProxy templates next to your playbooks, and to override the `haproxy_configuration_templates`variable to use them. Like that, you don't have to change the HAProxy role when you update your HAPrxoxy configuration.

More info about configuration at https://mcorbin.fr/posts/2018-01-26-ansible-templating/.

- `haproxy_extra_templates`: You can use it to template other configuration files: A list of map, each map having a `src`, `dest`, `owner`, `group`, `mode` key.
