"""AsgardServer URL Configuration

The `urlpatterns` list routes URLs to views. For more information please see:
    https://docs.djangoproject.com/en/1.8/topics/http/urls/
Examples:
Function views
    1. Add an import:  from my_app import views
    2. Add a URL to urlpatterns:  url(r'^$', views.home, name='home')
Class-based views
    1. Add an import:  from other_app.views import Home
    2. Add a URL to urlpatterns:  url(r'^$', Home.as_view(), name='home')
Including another URLconf
    1. Add an import:  from blog import urls as blog_urls
    2. Add a URL to urlpatterns:  url(r'^blog/', include(blog_urls))
"""
from django.conf.urls import include, url
from django.contrib import admin

urlpatterns = [
    url(r'^admin/', include(admin.site.urls)),
    #url(r'^get-events/(?P<log_id>\d+)$', 'Asgard_Services.views.get_events', name='get-changes'),
    url(r'^get-events', 'Asgard_Services.views.get_all_events'),
    url(r'^get-relationships', 'Asgard_Services.views.get_all_relationships'),
    url(r'^create-event', 'Asgard_Services.views.create_event'),
    url(r'^login', 'Asgard_Services.views.login'),
    url(r'^$', 'Asgard_Services.views.index'),
]
