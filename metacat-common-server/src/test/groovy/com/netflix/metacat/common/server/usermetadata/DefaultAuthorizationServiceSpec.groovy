/*
 *  Copyright 2018 Netflix, Inc.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package com.netflix.metacat.common.server.usermetadata

import com.netflix.metacat.common.QualifiedName
import com.netflix.metacat.common.exception.MetacatException
import com.netflix.metacat.common.server.properties.Config
import spock.lang.Shared
import spock.lang.Specification

class DefaultAuthorizationServiceSpec extends Specification{
    @Shared conf  = Mock(Config)

    def setupSpec() {
        conf.getMetacatCreateAcl() >> { return "prodhive/abc:bdp_janitor" }
        conf.getMetacatDeleteAcl() >> { return "prodhive/abc:bdp_janitor" }
    }

    def "Test unauthorized check"() {
        def authorizationService = new DefaultAuthorizationService(conf)
        when:
        def ret = authorizationService.isUnauthorized(null,
            QualifiedName.fromString("prodhive/abc") , MetacatACL.metacatCreate)
        then:
        ret
        when:
        ret = authorizationService.isUnauthorized(null ,
            QualifiedName.fromString("prodhive/abc") , MetacatACL.metacatDelete)
        then:
        ret
        when:
        ret = authorizationService.isUnauthorized(null ,
            QualifiedName.fromString("prodhive/default") , MetacatACL.metacatDelete)
        then:
        !ret
  }

    def "Test getAclMap"() {
        def authorizationService = new DefaultAuthorizationService(conf)
        expect:
        result.toString() == authorizationService.getACLMap(aclconfig).toString()
        where:
        aclconfig                    | result
        'prodhive/default:ursula,datauser|prodhive/vault_events:ursula,datauser|prodhive/ursula:ursula,datauser'     | '[prodhive/ursula:[datauser, ursula], prodhive/default:[datauser, ursula], prodhive/vault_events:[datauser, ursula]]'
        'prodhive/default:ursula,datauser'     | '[prodhive/default:[datauser, ursula]]'
    }

    def "Test getAclMap empty user"() {
        def authorizationService = new DefaultAuthorizationService(conf)
        when:
        def result = authorizationService.getACLMap('prodhive/default:')
        then:
        result.get('prodhive/default').isEmpty()
    }

    def "Test getAclMap format error"() {
        def authorizationService = new DefaultAuthorizationService(conf)
        when:
        authorizationService.getACLMap('prodhive/default')
        then:
        thrown MetacatException
    }
}
