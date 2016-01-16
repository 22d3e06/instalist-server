#!/usr/bin/env python3

import sys
import yaml
import codecs
from collections import OrderedDict

apiMethodsSpec = ["get", "add", "update", "delete"]
apiMethodsStandardTypes = ["get", "post", "put", "delete"]

apiPathNameMap = {}

# Maps our api definitions to the standard api types get, post, put, delete
def mapApiSpecToStandardSpec(_givenSpec):
    for index,specToTest in enumerate(apiMethodsSpec):
        if _givenSpec == specToTest:
            return apiMethodsStandardTypes[index]
    return None

def splitMethodName(_methodName):
    for index,specToTest in enumerate(apiMethodsSpec):
        if _methodName.startswith(specToTest):
            ret = []
            ret.append( specToTest)
            ret.append( _methodName[len(specToTest):])
            return ret
    return None

def utf8Write(_file, _string):
    _file.write(_string)

def main(argv):
    if len(argv) != 1:
        print('Usage: ' + sys.argv[0] + ' <spec-file>')
        exit(1)

    target = "shoppingApi.raml"
    contents = ""
    f = codecs.open(target, 'wb', "utf-8-sig")
    print('Writing contents to ' + target + ' ...')
    with open(argv[0], 'r') as source:
        contents = source.read()

    utf8Write(f, "#%RAML 0.8 \n")
    utf8Write(f, "title: InstantList API \n")
    utf8Write(f, "version: v1 \n")
    utf8Write(f, "baseUri: http://instantlist.noorganization.org/{version} \n")
    utf8Write(f, "mediaType: application/json \n\n")

    spec = yaml.load(contents)
    rpc_spec = spec['rpc']

    for current_rpc_method in rpc_spec:
        splittedName = splitMethodName(current_rpc_method)
        if splittedName[1] not in apiPathNameMap:
            apiPathNameMap[splittedName[1]] = {}
        apiPathNameMap[splittedName[1]][mapApiSpecToStandardSpec(splittedName[0])] = {}
    #print(apiPathNameMap)

    for current_rpc_method in rpc_spec:
        splittedName = splitMethodName(current_rpc_method)

        if len(rpc_spec[current_rpc_method]['parameters']) > 0:
            #print(splittedName[1])
            apiPathNameMap[splittedName[1]][mapApiSpecToStandardSpec(splittedName[0])]["params"] = {}
            for indexX, value in enumerate(rpc_spec[current_rpc_method]['parameters']):
                apiPathNameMap[splittedName[1]][mapApiSpecToStandardSpec(splittedName[0])]["params"][indexX] = value

        description = ""
        for description_line in rpc_spec[current_rpc_method]['description'].split("\n"):
            if len(description_line.strip(' \t\n\r')) == 0:
                continue
            description += description_line

        apiPathNameMap[splittedName[1]][mapApiSpecToStandardSpec(splittedName[0])]["desc"] = description
    # print(apiPathNameMap)
    OrderedDict(sorted(apiPathNameMap.items(), key=lambda t:t[0]))

    for methodNameKey, methodName in apiPathNameMap.iteritems():
        utf8Write(f, "/" + methodNameKey + ": \n")
        for methodTypeKey, methodType in methodName.iteritems():
            utf8Write(f, "\t" + methodTypeKey + ": \n")
            utf8Write(f, "\t\tdescription:|\n\t\t\t" + methodType["desc"] + ": \n")
            if "params" in methodType:
                if len(methodType["params"].keys()) > 0:
                    if methodTypeKey == "post":
                        utf8Write(f, "\t\tbody:\n")
                        utf8Write(f, "\t\t\tapplication/json:\n")
                        utf8Write(f, "\t\t\t\tschema:\n")
                        utf8Write(f, "\t\t\t\t\t" + methodNameKey + "\n")
                    else:
                        utf8Write(f, "\t\tqueryParameters:\n")
                        for paramKey, param in methodType["params"].iteritems():
                            utf8Write(f, "\t\t\t" + param + ":\n")
                            utf8Write(f, "\t\t\t\tdisplayName: todo\n")
                            utf8Write(f, "\t\t\t\ttype: todo\n")
                            utf8Write(f, "\t\t\t\tdescription: todo\n")
                            utf8Write(f, "\t\t\t\texample: todo\n")
                            utf8Write(f, "\t\t\t\trequired: true\n")

            utf8Write(f, "\t\t\taccess_token:\n")
            utf8Write(f, '\t\t\t\tdescription: "The access token provided by the authentication application"\n')
            utf8Write(f, "\t\t\t\texample: AABBCCDD\n")
            utf8Write(f, "\t\t\t\trequired: true\n")
            utf8Write(f, "\t\t\t\ttype: string\n")

            utf8Write(f, "\t\tresponses:\n")
            utf8Write(f, "\t\t\t200:\n")
            utf8Write(f, "\t\t\t\tbody:\n")
            utf8Write(f, "\t\t\t\t\tapplication/json:\n")
            utf8Write(f, "\t\t\t\t\t\texample:|\n")
            utf8Write(f, "\t\t\t\t\t\t\t{\n")
            utf8Write(f, "\t\t\t\t\t\t\t\ttodo\n")
            utf8Write(f, "\t\t\t\t\t\t\t}\n")
            utf8Write(f, "\t\t\t400:\n")
            utf8Write(f, "\t\t\t\tbody:\n")
            utf8Write(f, "\t\t\t\t\tapplication/json:\n")
            utf8Write(f, "\t\t\t\t\t\texample:|\n")
            utf8Write(f, "\t\t\t\t\t\t\t{\n")
            utf8Write(f, "\t\t\t\t\t\t\t\ttodo\n")
            utf8Write(f, "\t\t\t\t\t\t\t}\n")


main(sys.argv[1:])