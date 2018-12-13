/**********************************************************************************************************************
 * Copyright (c) 2018 Limark Technologies (Pvt) Ltd.                                                                  *
 *                                                                                                                    *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated      *
 *  documentation files (the "Software"), to deal in the Software without restriction, including without limitation   *
 *  the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and  *
 *  to permit persons to whom the Software is furnished to do so, subject to the following conditions:                *
 *                                                                                                                    *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of   *
 * the Software.                                                                                                      *
 *                                                                                                                    *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO   *
 * THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL         *
 * THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF      *
 * CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE                               *
 **********************************************************************************************************************/

package com.limark.open.gradle.plugins.gitflowsemver.core.strategies.impl

import com.limark.open.gradle.plugins.gitflowsemver.config.PluginConfig
import com.limark.open.gradle.plugins.gitflowsemver.core.GitClient
import com.limark.open.gradle.plugins.gitflowsemver.core.model.Version
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class BugfixBranchVersioningStrategy extends DevelopBranchVersioningStrategy {

  private static final Logger log = LoggerFactory.getLogger(this.getClass())

  BugfixBranchVersioningStrategy(GitClient gitClient, PluginConfig pluginConfig) {
    super(gitClient, pluginConfig)
  }

  @Override
  boolean supports(String branch) {
    return branch.startsWith("bugfix/")
  }

  @Override
  Version resolve() {
    Version version = super.resolve()
    String uniqueBranchId = gitClient.getUniqueBranchId(gitClient.getBranchName().substring("bugfix/".length()))
    version.setPreReleasePrefix("bugfix-${uniqueBranchId}")
    version.setPreRelease(gitClient.getCommitsSince("develop") + 1)
    return version
  }

}
