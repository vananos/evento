#!/usr/bin/php
<?php
    function exception_error_handler($errno, $errstr, $errfile, $errline ) {
        throw new ErrorException($errstr, $errno, 0, $errfile, $errline);
    }

    set_error_handler("exception_error_handler");

    function prettify_json($json_str) {
        return json_encode(json_decode($json_str), JSON_PRETTY_PRINT | JSON_UNESCAPED_SLASHES);
    }

    function call_api($uri, $method, $token) {
        $url = 'http://localhost:8080';

        echo "calling API $method ${url}${uri} with token $token\n";

        $options = array(
            'http' => array(
                'header'  => "Content-type: application/x-www-form-urlencoded\r\n"
                                ."$token\r\n",
                'method'  => "$method"
            )
        );
        $context  = stream_context_create($options);

        $body = file_get_contents("${url}${uri}", false, $context);

        return array(
        'body' => $body,
        'headers' => $http_response_header
        );
    }


    $response = call_api("/login", "POST", "");
    $auth_header = array_values(preg_grep('/^Authorization:.*$/', $response["headers"]))[0];

    $response = call_api("/getMovie?t=Mile&y=1999", "GET", $auth_header);
    echo prettify_json($response["body"]);
    echo "\n";
    $response = call_api("/getBook?isbn=ISBN:0451526538", "GET", $auth_header);
    echo prettify_json($response["body"]);
    echo "\n";

    echo("Good bye, thank you for your attention ^_^\n");
?>