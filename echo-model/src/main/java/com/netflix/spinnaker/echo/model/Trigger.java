/*
 * Copyright 2015 Netflix, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.netflix.spinnaker.echo.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.ToString;
import lombok.Value;
import lombok.experimental.Wither;

import java.util.List;
import java.util.Map;

@JsonDeserialize(builder = Trigger.TriggerBuilder.class)
@Builder(toBuilder = true)
@Wither
@ToString(of = {"type", "master", "job", "cronExpression", "source", "project", "slug", "account", "repository", "tag", "parameters", "payloadConstraints", "attributeConstraints", "branch", "runAsUser", "subscriptionName", "pubsubSystem", "expectedArtifactIds", "payload"}, includeFieldNames = false)
@Value
public class Trigger {
  public enum Type {
    CRON("cron"),
    GIT("git"),
    JENKINS("jenkins"),
    DOCKER("docker"),
    WEBHOOK("webhook"),
    PUBSUB("pubsub"),
    DRYRUN("dryrun");

    private final String type;

    Type(String type) {
      this.type = type;
    }

    @Override
    public String toString() {
      return type;
    }
  }

  boolean enabled;
  String id;
  String type;
  String master;
  String job;
  Integer buildNumber;
  String propertyFile;
  String cronExpression;
  String source;
  String project;
  String slug;
  String hash;
  Map parameters;
  String account;
  String repository;
  String tag;
  String digest;
  Map payloadConstraints;
  Map payload;
  Map attributeConstraints;
  String branch;
  String runAsUser;
  String secret;

  /**
   * Logical name given to the subscription by the user, not the locator
   * the pub/sub system uses.
   */
  String subscriptionName;
  String pubsubSystem;
  List<String> expectedArtifactIds;
  Map<String, ?> lastSuccessfulExecution;

  public Trigger atBuildNumber(final int buildNumber) {
    return this.toBuilder()
        .buildNumber(buildNumber)
        .hash(null)
        .tag(null)
        .build();
  }

  public Trigger atHash(final String hash) {
    return this.toBuilder()
        .buildNumber(null)
        .hash(hash)
        .tag(null)
        .build();
  }

  public Trigger atBranch(final String branch) {
    return this.toBuilder()
        .buildNumber(null)
        .tag(null)
        .branch(branch)
        .build();
  }

  public Trigger atTag(final String tag) {
    return this.toBuilder()
        .buildNumber(null)
        .hash(null)
        .tag(tag)
        .build();
  }

  public Trigger atPayload(final Map payload) {
    return this.toBuilder()
      .payload(payload)
      .build();
  }

  public Trigger atParameters(final Map parameters) {
    return this.toBuilder()
        .parameters(parameters)
        .build();
  }

  public Trigger atSecret(final String secret) {
    return this.toBuilder()
        .buildNumber(null)
        .hash(null)
        .digest(null)
        .secret(secret)
        .build();
  }

  public Trigger atMessageDescription(final String subscriptionName, final String pubsubSystem) {
    return this.toBuilder()
        .subscriptionName(subscriptionName)
        .pubsubSystem(pubsubSystem)
        .build();
  }

  @JsonPOJOBuilder(withPrefix = "")
  public static final class TriggerBuilder {
  }
}
