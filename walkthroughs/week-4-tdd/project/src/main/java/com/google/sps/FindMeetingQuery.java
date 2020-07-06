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
    List<TimeRange> unavailable_Time = new ArrayList<TimeRange>();
    List<TimeRange> available_Time = new ArrayList<TimeRange>();

    //Duration greater than a day is not valid, return empty list
    if (request.getDuration() > TimeRange.WHOLE_DAY.duration()) {
      return available_Time;
    }

    //If no required attendees or any events going on then it can be held at whatever time
    if (request.getAttendees().isEmpty() || events.isEmpty()) {
      available_Time.add(TimeRange.WHOLE_DAY);
      return available_Time;
    }
    
    //Add unavailable times if required attendees must also be at other events
    for (Event event : events) {
      for (String attendee : event.getAttendees()) {
        if (request.getAttendees().contains(attendee)) {
          unavailable_Time.add(event.getWhen());
          break;
        }
      }
    }

    //If there are no unvailable times, return the whole day
    if (unavailable_Time.isEmpty()) {
      available_Time.add(TimeRange.WHOLE_DAY);
      return available_Time;
    }

    //Remove unavailable TimeRanges that are contained in other TimeRanges
    for (int i = 0; i < unavailable_Time.size() - 1; i++) {
      for (int j = i + 1; j < unavailable_Time.size(); j++) {
        if (unavailable_Time.get(i).contains(unavailable_Time.get(j))) {
          unavailable_Time.remove(j);
        }
      }
    }

    //Calculate Free Times
    int start = TimeRange.START_OF_DAY;
    int end = TimeRange.END_OF_DAY;

    Collections.sort(unavailable_Time, TimeRange.ORDER_BY_START);

    for (TimeRange time : unavailable_Time) {
      if (time.start() - start >= request.getDuration()) {
        available_Time.add(TimeRange.fromStartEnd(start, time.start(), false));
      }
      start = time.end();
    }

    if (end - start >= request.getDuration()) {
      available_Time.add(TimeRange.fromStartEnd(start, end, true));
    }

    return available_Time;
  }
}
