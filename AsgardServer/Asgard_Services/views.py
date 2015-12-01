from django.shortcuts import render, redirect, get_object_or_404
from django.contrib.auth.models import User
from Asgard_Services.models import *
from django.http import HttpResponse
from datetime import datetime
from django.http import Http404
from django.views.decorators.csrf import csrf_exempt
from functools import wraps
from django.utils.decorators import available_attrs, decorator_from_middleware
from pytz import timezone
import pytz
from django.core.exceptions import ObjectDoesNotExist
from django.core.urlresolvers import reverse
from django.db import transaction
from django.contrib.auth import authenticate
from django.contrib.auth import login as login_func_from_django
from django.contrib.auth import logout as logout_func_from_django
from django.contrib.auth.tokens import default_token_generator
from django.contrib.auth.decorators import login_required
from django.core.mail import send_mail


@csrf_exempt
def create_event(request):
    print("Step into the first step.")
    try:
        launcher = User.objects.get(username=request.POST['username']).user_profile
    except User.DoesNotExist:
        raise Http404("No User matches the given query.")

    print("Step into the second step.")
    date = datetime.fromtimestamp(int(request.POST['time']), pytz.UTC)

    try:
        event = Event(name=str(request.POST['name']),
                      venue=str(request.POST['venue']),
                      description=str(request.POST['description']),
                      dress_code=str(request.POST['dress_code']),
                      target_audience=str(request.POST['target_audience']),
                      max_people=int(request.POST['max_people']),
                      launcher=launcher,
                      data=date,
                      post=request.FILES['picture']
                      )
        event.save()
    except Exception as e:
        print(e.message)
    #image = request.FILES['picture']
    #image.save()
    print("Step into this step.")
    return HttpResponse("Event created successfully.", content_type="text/plain")


def get_all_events(request):
    events = Event.objects.all()
    context = {"total_amounts": events.count(), "events": events}
    return render(request, 'events.json', context, content_type='application/json')


def create_user(request):
    new_user = User(username=request.POST['username'])
    new_user.set_password(request.POST['password'])
    new_user.save()
    new_user_profile = UserProfile(user=new_user,
                                   phone_num=request.POST['phone_num'],
                                   gender=request.POST['gender'])
    new_user_profile.save()
    return HttpResponse("User created successfully.", content_type="text/plain")


def index(request):
    return render(request, 'index.html', {})


def get_all_relationships(request):
    relationships = []
    events = Event.objects.all()
    for event in events:
        participants = event.participant.all()
        if participants:
            for participant in participants:
                relationships.append(Relationship(event.id, participant.user.username))
    return render(request, 'relationships.json', {'relationships': relationships}, content_type='application/json')


class Relationship:
    def __init__(self, event_id, participant_username):
        self.event_id = event_id
        self.participant_username = participant_username
