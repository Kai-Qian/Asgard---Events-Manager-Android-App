from django.shortcuts import render, redirect, get_object_or_404
from django.contrib.auth.models import User
from Asgard_Services.models import *
from django.http import HttpResponse
from datetime import datetime
from django.http import Http404
from django.views.decorators.csrf import csrf_exempt
from django.contrib.auth import authenticate
import pytz
import traceback


@csrf_exempt
def login(request):
    username = str(request.POST['username'])
    password = str(request.POST['password'])
    username = username.replace("\r\n", "")
    password = password.replace("\r\n", "")
    user = authenticate(username=username, password=password)
    if user is not None:
        return HttpResponse("OK", content_type="text/plain")
    else:
        return HttpResponse("NOT OK", content_type="text/plain")


@csrf_exempt
def create_event(request):
    try:
        launcher = User.objects.get(username=request.POST['username']).user_profile
    except User.DoesNotExist:
        raise Http404("No User matches the given query.")

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
        print(request.FILES['picture'])
    except Exception as e:
        print(e.message)
        traceback.print_exc()
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
                relationships.append(Relationship(event.id,
                                                  participant.user.username,
                                                  str(event.pk) + ":" + str(participant.pk)))
    return render(request, 'relationships.json', {'relationships': relationships}, content_type='application/json')


class Relationship:
    def __init__(self, event_id, participant_username, unique_id):
        self.unique_id = unique_id
        self.event_id = event_id
        self.participant_username = participant_username
