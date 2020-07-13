// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.sps;

import java.util.*;

public final class FindMeetingQuery {
  public Collection<TimeRange> query(Collection<Event> events, MeetingRequest request) {
    List<TimeRange> ununavailableTime = new ArrayList<TimeRange>();
    List<TimeRange> availableTime = new ArrayList<TimeRange>();

    //Duration greater than a day is not valid, return empty list
    if (request.getDuration() > TimeRange.WHOLE_DAY.duration()) {
      return availableTime;
    }

    //If no required attendees or any events going on then it can be held at whatever time
    if (request.getAttendees().isEmpty() || events.isEmpty()) {
      availableTime.add(TimeRange.WHOLE_DAY);
      return availableTime;
    }
    
    //Add unavailable times if required attendees must also be at other events
    for (Event event : events) {
      for (String attendee : event.getAttendees()) {
        if (request.getAttendees().contains(attendee)) {
          ununavailableTime.add(event.getWhen());
          break;
        }
      }
    }

    //If there are no unavailable times, return the whole day
    if (ununavailableTime.isEmpty()) {
      availableTime.add(TimeRange.WHOLE_DAY);
      return availableTime;
    }

    //Remove unavailable TimeRanges that are contained in other TimeRanges
    for (int i = 0; i < ununavailableTime.size() - 1; i++) {
      for (int j = i + 1; j < ununavailableTime.size(); j++) {
        if (ununavailableTime.get(i).contains(ununavailableTime.get(j))) {
          ununavailableTime.remove(j);
        }
      }
    }

    //Calculate Free Times
    int start = TimeRange.START_OF_DAY;
    int end = TimeRange.END_OF_DAY;

    Collections.sort(ununavailableTime, TimeRange.ORDER_BY_START);

    for (TimeRange time : ununavailableTime) {
      if (time.start() - start >= request.getDuration()) {
        availableTime.add(TimeRange.fromStartEnd(start, time.start(), /* inclusive= */ false));
      }
      start = time.end();
    }

    if (end - start >= request.getDuration()) {
      availableTime.add(TimeRange.fromStartEnd(start, end, true));
    }

    return availableTime;
  }
}
