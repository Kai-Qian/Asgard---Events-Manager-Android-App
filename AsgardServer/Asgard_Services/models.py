from django.db import models
from django.db.models import Max
from django.contrib.auth.models import User
from django.contrib.auth.models import AbstractBaseUser
from django.shortcuts import get_object_or_404
from django.template import loader, Context
from django.utils.html import escape
from datetime import timedelta
# Create your models here.


class Event(models.Model):
    name = models.CharField(max_length=420)
    venue = models.CharField(max_length=420)
    description = models.CharField(max_length=420)
    dress_code = models.CharField(max_length=420)
    data = models.DateTimeField(editable=True)
    modified = models.DateTimeField(auto_now=True)
    target_audience = models.CharField(max_length=420)
    max_people = models.IntegerField()
    post = models.ImageField(upload_to="media", blank=True)
    launcher = models.ForeignKey('UserProfile')

    def __unicode__(self):
        return self.name

    @property
    def date(self):
        return int(self.data.strftime("%s"))

    @property
    def modified_date(self):
        return int(self.modified.strftime("%s"))


class UserProfile(models.Model):
    user = models.OneToOneField(User, related_name='user_profile')
    phone_num = models.CharField(max_length=100)
    gender = models.CharField(max_length=100)
    participate_events = models.ManyToManyField(Event, symmetrical=True, related_name='participant', blank=True)

    def __unicode__(self):
        return self.user.username

