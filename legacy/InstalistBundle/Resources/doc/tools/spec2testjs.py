#!/usr/bin/env python3

import sys
import yaml
import inflection

def main(argv):
    if len(argv) != 1:
        print('Usage: ' + sys.argv[0] + ' <spec-file>')
        exit(1)

    target = "../../../Resources/public/tests/tests-list.js"
    contents = ""
    f = open(target, 'wb')
    print('Writing contents to ' + target + ' ...')
    with open(argv[0], 'r') as source:
        contents = source.read()
    
    f.write(bytes('var connection;\n\n', 'UTF-8'))

    f.write(bytes('QUnit.module("Test RPC API", function (hooks) {\n', 'UTF-8'))
    f.write(bytes('    var session = null;\n', 'UTF-8'))
    f.write(bytes('    hooks.beforeEach(function (assert) {\n', 'UTF-8'))
    f.write(bytes('        var that = this;\n', 'UTF-8'))
    f.write(bytes('        var done1 = assert.async(1);\n', 'UTF-8'))
    f.write(bytes('        console.log("Set up");\n', 'UTF-8'))
    f.write(bytes('        ab.connect(\n', 'UTF-8'))
    f.write(bytes('             // The WebSocket URI of the WAMP server\n', 'UTF-8'))
    f.write(bytes('             wstarget,\n', 'UTF-8'))

    f.write(bytes('             // The onconnect handler\n', 'UTF-8'))
    f.write(bytes('             sessionEstablished,\n', 'UTF-8'))
    f.write(bytes('             // hanup handler when somehting bad happens\n', 'UTF-8'))
    f.write(bytes('             hangUpHandler,\n', 'UTF-8'))
    f.write(bytes('             // The session options\n', 'UTF-8'))
    f.write(bytes('             {\n', 'UTF-8'))
    f.write(bytes('                 \'maxRetries\': 0,\n', 'UTF-8'))
    f.write(bytes('                 \'retryDelay\': 0\n', 'UTF-8'))
    f.write(bytes('             }\n', 'UTF-8'))
    f.write(bytes('         );\n', 'UTF-8'))

    f.write(bytes('         function sessionEstablished(_session) {\n', 'UTF-8'))
    f.write(bytes('             console.log("Test setup session: ");\n', 'UTF-8'))
    f.write(bytes('             console.log(_session);\n', 'UTF-8'))
    f.write(bytes('             session = _session;\n', 'UTF-8'))
    f.write(bytes('             done1();\n', 'UTF-8'))
    f.write(bytes('         }\n', 'UTF-8'))

    f.write(bytes('         function hangUpHandler(code, reason, detail) {\n', 'UTF-8'))
    f.write(bytes('             // WAMP session closed here ..\n', 'UTF-8'))
    f.write(bytes('             console.log(code + " " + reason);\n', 'UTF-8'))
    f.write(bytes('             console.log(detail);\n', 'UTF-8'))
    f.write(bytes('             done1();\n', 'UTF-8'))
    f.write(bytes('         }\n', 'UTF-8'))
    f.write(bytes('     });\n\n', 'UTF-8'))
    f.write(bytes('     hooks.afterEach(function (assert){\n', 'UTF-8'))
    f.write(bytes('     });\n', 'UTF-8'))

    spec = yaml.load(contents)
    rpc_spec = spec['rpc']
    for current_rpc_method in rpc_spec:
        f.write(bytes('    QUnit.test("RPC Call ' + current_rpc_method + '!", function (assert) { \n', 'UTF-8'))
        f.write(bytes('        var done = assert.async(1); \n', 'UTF-8'))
        f.write(bytes('        // do something here that requires open connection... \n', 'UTF-8'))
        f.write(bytes('        // WAMP session established here .. \n', 'UTF-8'))
        f.write(bytes('        session.call("instalist/' + inflection.underscore(current_rpc_method) + '", {', 'UTF-8'))            
        if len(rpc_spec[current_rpc_method]['parameters']) == 0:
            f.write(bytes("", 'UTF-8'))
        else:
            f.write(bytes(', '.join(rpc_spec[current_rpc_method]['parameters']), 'UTF-8'))
        f.write(bytes('}).then(function (result) { \n', 'UTF-8'))
        f.write(bytes('            console.log("result ' + current_rpc_method + '");\n', 'UTF-8'))
        f.write(bytes('            console.log(result);\n', 'UTF-8'))
        f.write(bytes('            done();\n', 'UTF-8'))
        f.write(bytes('            // do stuff with the result\n', 'UTF-8'))
        f.write(bytes('            }, function (error) {\n', 'UTF-8'))
        f.write(bytes('                // handle the error\n', 'UTF-8'))
        f.write(bytes('                console.error("Error in rpc ' + current_rpc_method + '!" + error);\n', 'UTF-8'))
        f.write(bytes('                done();\n', 'UTF-8'))
        f.write(bytes('            });\n', 'UTF-8'))
        f.write(bytes('    });\n\n', 'UTF-8'))        
    
    f.write(bytes('});\n\n', 'UTF-8'))

main(sys.argv[1:])

