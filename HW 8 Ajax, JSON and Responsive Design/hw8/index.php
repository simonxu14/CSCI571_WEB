<?php
    header('Access-Control-Allow-Origin: *');
    if(!empty($_GET)) {
        fetch($_GET);
    }

    function fetch($data) {
        if ($data["operation"] == "legislators") {
            $url = "http://104.198.0.197:8080/legislators?apikey=de0223ad155f4783bd1c22edbf49673d&per_page=all";
            $contents = file_get_contents($url);
            header('Content-type: application/json');

            echo $contents;
        }



        if ($data["operation"] == "bills") {
            if ($data["sponsor_id"] != null) {
                $url = "http://104.198.0.197:8080/bills?sponsor_id=" . $data["sponsor_id"] . "&apikey=de0223ad155f4783bd1c22edbf49673d&per_page=50";
                $contents = file_get_contents($url);
                header('Content-type: application/json');

                echo $contents;
            }
            if ($data["active"] != null) {
                $url = "http://104.198.0.197:8080/bills?history.active=" . $data["active"] . "&apikey=de0223ad155f4783bd1c22edbf49673d&per_page=50";
                $contents = file_get_contents($url);
                header('Content-type: application/json');

                echo $contents;
            }
        }

        if ($data["operation"] == "committees") {
            $url = "http://104.198.0.197:8080/committees?&apikey=de0223ad155f4783bd1c22edbf49673d&per_page=all";
            $contents = file_get_contents($url);
            header('Content-type: application/json');

            echo $contents;
        }
    }
?>