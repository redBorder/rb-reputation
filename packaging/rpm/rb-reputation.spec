%undefine __brp_mangle_shebangs

Name: rb-reputation
Version: %{__version}
Release: %{__release}%{?dist}
BuildArch: noarch
Summary: Main package for rb-reputation

License: AGPL 3.0
URL: https://github.com/redBorder/rb-reputation
Source0: %{name}-%{version}.tar.gz

BuildRequires: maven

%description
%{summary}

%prep
%setup -qn %{name}-%{version}

%build

mvn install:install-file \
  -Dfile=resources/maven/VirustotalPublicV2.0-2.0.jar \
  -DgroupId=com.kanishka.api \
  -DartifactId=VirustotalPublicV2.0 \
  -Dversion=2.0 \
  -Dpackaging=jar

mvn package

%install
install -D -m 0644 resources/systemd/rb-reputation.service %{buildroot}/usr/lib/systemd/system/rb-reputation.service

mkdir -p %{buildroot}/opt/rb-reputation/app/
cp target/rb-reputation-1.1.5-SNAPSHOT-selfcontained.jar %{buildroot}/opt/rb-reputation/app/

mkdir -p %{buildroot}/etc/rb-reputation
cp -r resources/config/*  %{buildroot}/etc/rb-reputation/

%pre

%post
[ ! -f /opt/rb-reputation/app/rb-reputation.jar ] && ln -s /opt/rb-reputation/app/rb-reputation-1.1.5-SNAPSHOT-selfcontained.jar /opt/rb-reputation/app/rb-reputation.jar

systemctl daemon-reload

%files
%defattr(0755,root,root)
/opt/rb-reputation
/etc/rb-reputation
%defattr(0644,root,root)
/usr/lib/systemd/system/rb-reputation.service
/opt/rb-reputation/app/rb-reputation-1.1.5-SNAPSHOT-selfcontained.jar

%doc

%changelog
* Thu Sep 16 2025 manegron <manegron@redborder.com> - 0.0.1-1
- first spec version
