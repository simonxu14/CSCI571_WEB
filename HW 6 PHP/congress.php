<!DOCTYPE html>
<head>
    <title>Forecast</title>
    <style>
        div#search {margin:auto; text-align:center;}
        div#zero {margin:auto; text-align:center;}
        div#myform {margin:auto; text-align:center; width:300px; border:1px solid rgb(192,192,192);}
        div#list {margin:auto; text-align:center;}
        div#detail {margin:auto; text-align:center; width:800px; border:1px solid black;}
        table#list {margin:auto; text-align:center; border:1px solid black; border-collapse:collapse; width:800px;}
        table#list td {border:1px solid black;}
        table#list td#left {border:1px solid black; padding-left:80px; text-align:left;}
        table#detail {margin:auto; text-align:center; width:600px;}
        table#detail td {margin:auto; text-align:left; padding-left:100px;}

    </style>
</head>
<script>
    function select(select){
        var value = select.value;
        if (value == "legislators") {
            document.getElementById("replace").innerHTML = "<td>State/Representative*</td><td><input type='text' name='state'></td>";
        } else if (value == "committees") {
            document.getElementById("replace").innerHTML = "<td>Committee ID*</td><td><input type='text' name='committee_id'></td>";
        } else if (value == "bills") {
            document.getElementById("replace").innerHTML = "<td>Bill ID*</td><td><input type='text' name='bill_id'></td>";
        } else if (value == "amendments") {
            document.getElementById("replace").innerHTML = "<td>Amendment ID*</td><td><input type='text' name='amendment_id'></td>";
        }
    }

    function validateForm() {
        var str = "Please enter the following missing information: ";
        var flag = 0;
        if (document.forms["search"]["database"].value == "select_your_option") {
            str += "Congress database";
            flag = 1;
        }
        if (document.getElementsByName("keyword").length == 1) {
            if(document.forms["search"]["keyword"].value == null || document.forms["search"]["keyword"].value.trim() == "") {
                if (flag == 1)
                    str += ", ";
                str += "keyword";
                flag = 2;
            }
        }
        if (document.getElementsByName("state").length == 1) {
            if(document.forms["search"]["state"].value == null || document.forms["search"]["state"].value.trim() == "") {
                if (flag == 1)
                    str += ", ";
                str += "keyword";
                flag = 2;
            }
        }
        if (document.getElementsByName("committee_id").length == 1) {
            if(document.forms["search"]["committee_id"].value == null || document.forms["search"]["committee_id"].value.trim() == "") {
                if (flag == 1)
                    str += ", ";
                str += "keyword";
                flag = 2;
            }
        }
        if (document.getElementsByName("bill_id").length == 1) {
            if(document.forms["search"]["bill_id"].value == null || document.forms["search"]["bill_id"].value.trim() == "") {
                if (flag == 1)
                    str += ", ";
                str += "keyword";
                flag = 2;
            }
        }
        if (document.getElementsByName("amendment_id").length == 1) {
            if(document.forms["search"]["amendment_id"].value == null || document.forms["search"]["amendment_id"].value.trim() == "") {
                if (flag == 1)
                    str += ", ";
                str += "keyword";
                flag = 2;
            }
        }
        if (flag >= 1) {
            alert(str);
            return false;
        }
        else
            return true;
    }
    
    function clear_form() {
        document.getElementById("replace").innerHTML = "<td>Keyword*</td><td><input type='text' name='keyword' id='keyword'></td>";
        document.getElementById("search").reset();
    }

    function pagechange(id) {
        document.getElementById("list").style.display = "none";
        document.getElementsByName(bioguide_id)[0].stype.display = "block";
        return false;
    }


</script>

<body>
    <div id="search">
        <h2 id="title">Congress Information Search</h2>
        <div id="myform">
            <form method="POST" name="search" action="congress.php" onsubmit="return validateForm()">
                <table>
                <tr>
                    <td>Congress Database</td>
                    <td>
                        <select id="database" name="database" onchange="select(this)">
                            <?php
                                $flag = -1;
                                if(!empty($_POST)){
                                    if ($_POST["database"] == "select_your_option")
                                        $flag = 0;
                                    else if ($_POST["database"] == "legislators")
                                        $flag = 1;
                                    else if ($_POST["database"] == "committees")
                                        $flag = 2;
                                    else if ($_POST["database"] == "bills")
                                        $flag = 3;
                                    else if ($_POST["database"] == "amendments")
                                        $flag = 4;
                                }else if(!empty($_GET)){
                                    if ($_GET["state"] == "select_your_option")
                                        $flag = 0;
                                    else if ($_GET["database"] == "legislators")
                                        $flag = 1;
                                    else if ($_GET["database"] == "committees")
                                        $flag = 2;
                                    else if ($_GET["database"] == "bills")
                                        $flag = 3;
                                    else if ($_GET["database"] == "amendments")
                                        $flag = 4;
                                }
                            ?>
                            <option <?php if ($flag == -1 || $flag == 0) echo "selected"?> value="select_your_option" disabled="disabled">Select your option</option>
                            <option <?php if ($flag == 1) echo "selected"?> value="legislators">Legislators</option>
                            <option <?php if ($flag == 2) echo "selected"?> value="committees">Committees</option>
                            <option <?php if ($flag == 3) echo "selected"?> value="bills">Bills</option>
                            <option <?php if ($flag == 4) echo "selected"?> value="amendments">Amendments</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>Chamber</td>
                    <td>
                        <?php
                        $flag = -1;
                        if(!empty($_POST)){
                            if ($_POST["chamber"] == "senate")
                                $flag = 0;
                            else if ($_POST["chamber"] == "house")
                                $flag = 1;
                        }else if(!empty($_GET)){
                            if ($_GET["chamber"] == "senate")
                                $flag = 0;
                            else if ($_GET["chamber"] == "house")
                                $flag = 1;
                        }
                        ?>
                        <input type="radio" name="chamber" value="senate" <?php if ($flag == -1 || $flag == 0) echo "checked"?>>Senate
                        <input type="radio" name="chamber" value="house" <?php if ($flag == 1) echo "checked"?>>House
                    </td>
                <tr id="replace">


                        <?php
                        $flag = -1;
                        if(!empty($_POST)){
                            if (array_key_exists("state", $_POST)) {
                                $flag = 0;
                                $state = $_POST["state"];
                            }
                            else if (array_key_exists("committee_id", $_POST)) {
                                $flag = 1;
                                $committee_id = $_POST["committee_id"];
                            }
                            else if (array_key_exists("bill_id", $_POST)) {
                                $flag = 2;
                                $bill_id = $_POST["bill_id"];
                            }
                            else if (array_key_exists("amendment_id", $_POST)) {
                                $flag = 3;
                                $amendment_id = $_POST["amendment_id"];
                            }
                        }else if(!empty($_GET)){
                            if (array_key_exists("state", $_GET)) {
                                $flag = 0;
                                $state = $_GET["state"];
                            }
                            else if (array_key_exists("committee_id", $_GET)) {
                                $flag = 1;
                                $committee_id = $_GET["committee_id"];
                            }
                            else if (array_key_exists("bill_id", $_GET)) {
                                $flag = 2;
                                $bill_id = $_GET["bill_id"];
                            }
                            else if (array_key_exists("amendment_id", $_GET)) {
                                $flag = 3;
                                $amendment_id = $_GET["amendment_id"];
                            }
                        }

                        if ($flag == -1)
                            echo "<td>Keyword*</td><td><input type='text' name='keyword' id='keyword'></td>";
                        else if ($flag == 0) {
                            if (array_key_exists("query", $_GET))
                                echo "<td>State/Representative*</td><td><input type='text' name='state' value='" . $_GET['query'] . "'></td>";
                            else if (array_key_exists("query", $_POST))
                                echo "<td>State/Representative*</td><td><input type='text' name='state' value='" . $_POST['query'] . "'></td>";
                            else
                                echo "<td>State/Representative*</td><td><input type='text' name='state' value='" . $state . "'></td>";
                        }
                        else if ($flag == 1)
                            echo "<td>Committee ID*</td><td><input type='text' name='committee_id' value='". $committee_id ."'></td>";
                        else if ($flag == 2)
                            echo "<td>Bill ID*</td><td><input type='text' name='bill_id' value='". $bill_id ."'></td>";
                        else if ($flag == 3)
                            echo "<td>Amendment ID*</td><td><input type='text' name='amendment_id' value='". $amendment_id ."'></td>";
                        ?>

                </tr>
                <tr>
                    <td></td>
                    <td>
                        <input type="submit" value="Submit">
                        <input type="reset" value="Clear" onclick="clear_form()">
                    </td>
                </tr>
                </table>
                <a href="http://sunlightfoundation.com/">Power by Sunlight Foundation</a>
        </div>

        <br>
        <br>

        <?php
        if(!empty($_POST)) {
            get_list($_POST);
        }
        else if(!empty($_GET)) {
            get_detail($_GET);
        }
        ?>

        <?php
        function get_state_code($fullname) {
            $array_state[strtoupper("Alabama")] = "AL";
            $array_state[strtoupper("Alaska")] = "AK";
            $array_state[strtoupper("Arizona")] = "AZ";
            $array_state[strtoupper("Arkansas")] = "AR";
            $array_state[strtoupper("California")] = "CA";
            $array_state[strtoupper("Colorado")] = "CO";
            $array_state[strtoupper("Connecticut")] = "CT";
            $array_state[strtoupper("Delaware")] = "DE";
            $array_state[strtoupper("District OfColumbia")] = "DC";
            $array_state[strtoupper("Montana")] = "MT";
            $array_state[strtoupper("Nebraska")] = "NE";
            $array_state[strtoupper("Nevada")] = "NV";
            $array_state[strtoupper("New Hampshire")] = "NH";
            $array_state[strtoupper("New Jersey")] = "NJ";
            $array_state[strtoupper("New Mexico")] = "NM";
            $array_state[strtoupper("New York")] = "NY";
            $array_state[strtoupper("North Carolina")] = "NC";
            $array_state[strtoupper("North Dakota")] = "ND";
            $array_state[strtoupper("Florida")] = "FL";
            $array_state[strtoupper("Georgia")] = "GA";
            $array_state[strtoupper("Hawaii")] = "HI";
            $array_state[strtoupper("Idaho")] = "ID";
            $array_state[strtoupper("Illinois")] = "IL";
            $array_state[strtoupper("Indiana")] = "IN";
            $array_state[strtoupper("Iowa")] = "IA";
            $array_state[strtoupper("Kansas")] = "KS";
            $array_state[strtoupper("Kentucky")] = "KY";
            $array_state[strtoupper("Louisiana")] = "LA";
            $array_state[strtoupper("Maine")] = "ME";
            $array_state[strtoupper("Maryland")] = "MD";
            $array_state[strtoupper("Massachusetts")] = "MA";
            $array_state[strtoupper("Michigan")] = "MI";
            $array_state[strtoupper("Minnesota")] = "MN";
            $array_state[strtoupper("Mississippi")] = "MS";
            $array_state[strtoupper("Missouri")] = "MO";
            $array_state[strtoupper("Ohio")] = "OH";
            $array_state[strtoupper("Oklahoma")] = "OK";
            $array_state[strtoupper("Oregon")] = "OR";
            $array_state[strtoupper("Pennsylvania")] = "PA";
            $array_state[strtoupper("Rhode Island")] = "RI";
            $array_state[strtoupper("South Carolina")] = "SC";
            $array_state[strtoupper("South Dakota")] = "SD";
            $array_state[strtoupper("Tennessee")] = "TN";
            $array_state[strtoupper("Texas")] = "TX";
            $array_state[strtoupper("Utah")] = "UT";
            $array_state[strtoupper("Vermont")] = "VT";
            $array_state[strtoupper("Virginia")] = "VA";
            $array_state[strtoupper("Washington")] = "WA";
            $array_state[strtoupper("West Virginia")] = "WV";
            $array_state[strtoupper("Wisconsin")] = "WI";
            $array_state[strtoupper("Wyoming")] = "WY";
            if (array_key_exists(strtoupper($fullname), $array_state))
                return $array_state[strtoupper($fullname)];
            else
                return "no_exist";

        }

        function get_fullname($code) {
            $array_state["Alabama"] = "AL";
            $array_state["Alaska"] = "AK";
            $array_state["Arizona"] = "AZ";
            $array_state["Arkansas"] = "AR";
            $array_state["California"] = "CA";
            $array_state["Colorado"] = "CO";
            $array_state["Connecticut"] = "CT";
            $array_state["Delaware"] = "DE";
            $array_state["District OfColumbia"] = "DC";
            $array_state["Montana"] = "MT";
            $array_state["Nebraska"] = "NE";
            $array_state["Nevada"] = "NV";
            $array_state["New Hampshire"] = "NH";
            $array_state["New Jersey"] = "NJ";
            $array_state["New Mexico"] = "NM";
            $array_state["New York"] = "NY";
            $array_state["North Carolina"] = "NC";
            $array_state["North Dakota"] = "ND";
            $array_state["Florida"] = "FL";
            $array_state["Georgia"] = "GA";
            $array_state["Hawaii"] = "HI";
            $array_state["Idaho"] = "ID";
            $array_state["Illinois"] = "IL";
            $array_state["Indiana"] = "IN";
            $array_state["Iowa"] = "IA";
            $array_state["Kansas"] = "KS";
            $array_state["Kentucky"] = "KY";
            $array_state["Louisiana"] = "LA";
            $array_state["Maine"] = "ME";
            $array_state["Maryland"] = "MD";
            $array_state["Massachusetts"] = "MA";
            $array_state["Michigan"] = "MI";
            $array_state["Minnesota"] = "MN";
            $array_state["Mississippi"] = "MS";
            $array_state["Missouri"] = "MO";
            $array_state["Ohio"] = "OH";
            $array_state["Oklahoma"] = "OK";
            $array_state["Oregon"] = "OR";
            $array_state["Pennsylvania"] = "PA";
            $array_state["Rhode Island"] = "RI";
            $array_state["South Carolina"] = "SC";
            $array_state["South Dakota"] = "SD";
            $array_state["Tennessee"] = "TN";
            $array_state["Texas"] = "TX";
            $array_state["Utah"] = "UT";
            $array_state["Vermont"] = "VT";
            $array_state["Virginia"] = "VA";
            $array_state["Washington"] = "WA";
            $array_state["West Virginia"] = "WV";
            $array_state["Wisconsin"] = "WI";
            $array_state["Wyoming"] = "WY";
            return array_search($code, $array_state);
        }


        function get_list($data) {
            if ($data["database"] == "legislators") {
                echo "<div id='list'>";
                $database = $data["database"];
                $chamber = $data["chamber"];

                $state = get_state_code(trim($data["state"]));
                if ($state == "no_exist")
                    $url = "http://congress.api.sunlightfoundation.com/" . $database . "?chamber=" . $chamber . "&query=" . rawurlencode($data["state"]) . "&apikey=de0223ad155f4783bd1c22edbf49673d";
                else
                    $url = "http://congress.api.sunlightfoundation.com/" . $database . "?chamber=" . $chamber . "&state=" . $state . "&apikey=de0223ad155f4783bd1c22edbf49673d";

//                $ch = curl_init();
//                curl_setopt($ch, CURLOPT_URL, $url);
//                curl_setopt($ch, CURLOPT_RETURNTRANSFER, 1);
//                $result = json_decode(curl_exec($ch), true);
//                curl_close($ch);

                $contents = file_get_contents($url);
                $result = json_decode($contents, true);
                $array = $result["results"];
                if (sizeof($array) == 0) {
                    echo '<div id="zero">The API returned zero results for the request.</div>';
                    return;
                }
                echo "<table id='list'><tr><td><b>Name</b></td><td><b>State</b></td><td><b>Chamber</b></td><td><b>Details</b></td></tr>";
                $data_array = array();
                foreach ($array as $ele) {
                    echo '<tr>';
                    echo '<td id="left">' . $ele["first_name"] . " " . $ele["last_name"] . '</td>';
                    echo '<td>' . get_fullname($ele["state"]) . '</td>';
                    echo '<td>' . $ele["chamber"] . '</td>';
                    $newurl = "congress.php?database=" . $database . "&chamber=" . $chamber . "&query=" . $data["state"] . "&state=" . get_fullname($ele["state"]) . "&bioguide_id=" . $ele["bioguide_id"];
                    echo "<td><a onClick='pagechange&#40;&quot;". $ele["bioguide_id"] ."&quot;&#41;'>View Details</a>";
                    echo '</tr>';
                    $data_ori = array("database"=>$database, "chamber"=>$chamber, "query"=>$data["state"], "state"=>get_fullname($ele["state"]), "bioguide_id"=>$ele["bioguide_id"]);
                    array_push($data_array,$data_ori);
                }
                echo "</table>";
                echo "</div>";

                foreach($data_array as $ele)
                    get_detail($ele);



            }
            else if ($data["database"] == "committees") {
                echo "<div id='list'>";
                $database = $data["database"];
                $chamber = $data["chamber"];
                $committee_id = trim($data["committee_id"]);
                $url = "http://congress.api.sunlightfoundation.com/" . $database . "?chamber=" . $chamber . "&committee_id=" . rawurlencode(strtoupper($committee_id)) . "&apikey=de0223ad155f4783bd1c22edbf49673d";
//                $ch = curl_init();
//                curl_setopt($ch, CURLOPT_URL, $url);
//                curl_setopt($ch, CURLOPT_RETURNTRANSFER, 1);
//                $result = json_decode(curl_exec($ch), true);
//                curl_close($ch);
                $contents = file_get_contents($url);
                $result = json_decode($contents, true);

                $array = $result["results"];
                if (sizeof($array) == 0) {
                    echo '<div id="zero">The API returned zero results for the request.</div>';
                    return;
                }
                echo "<table id='list'><tr><td><b>Committee ID</b></td><td><b>Committee Name</b></td><td><b>Chamber</b></td></tr>";
                foreach ($array as $ele) {
                    echo '<tr>';
                    echo '<td>' . $ele["committee_id"] . '</td>';
                    echo '<td>' . $ele["name"] . '</td>';
                    echo '<td>' . $ele["chamber"] . '</td>';
                    echo '</tr>';
                }
                echo "</table>";
                echo "</div>";
            }
            else if ($data["database"] == "bills") {
                echo "<div id='list'>";
                $database = $data["database"];
                $chamber = $data["chamber"];
                $bill_id = trim($data["bill_id"]);
                $url = "http://congress.api.sunlightfoundation.com/" . $database . "?chamber=" . $chamber . "&bill_id=" . rawurlencode(strtolower($bill_id)) . "&apikey=de0223ad155f4783bd1c22edbf49673d";
//                $ch = curl_init();
//                curl_setopt($ch, CURLOPT_URL, $url);
//                curl_setopt($ch, CURLOPT_RETURNTRANSFER, 1);
//                $result = json_decode(curl_exec($ch), true);
//                curl_close($ch);
                $contents = file_get_contents($url);
                $result = json_decode($contents, true);

                $array = $result["results"];
                if (sizeof($array) == 0) {
                    echo '<div id="zero">The API returned zero results for the request.</div>';
                    return;
                }
                echo "<table id='list'><tr><td><b>Bill ID</b></td><td><b>Short Title</b></td><td><b>Chamber</b></td><td><b>Details</b></td></tr>";
                foreach ($array as $ele) {
                    echo '<tr>';
                    echo '<td>' . $ele["bill_id"] . '</td>';
                    if ($ele["short_title"] == null)
                        echo '<td>' . "NA" . '</td>';
                    else
                        echo '<td>' . $ele["short_title"] . '</td>';
                    echo '<td>' . $ele["chamber"] . '</td>';
                    $newurl = "congress.php?database=" . $database . "&chamber=" . $chamber . "&bill_id=" . $data["bill_id"];
                    echo '<td><a href="' . $newurl . '">View Details</a>';
                    echo '</tr>';
                }
                echo "</table>";
                echo "</div>";
            }
            else if ($data["database"] == "amendments") {
                echo "<div id='list'>";
                $database = $data["database"];
                $chamber = $data["chamber"];
                $amendment_id = trim($data["amendment_id"]);
                $url = "http://congress.api.sunlightfoundation.com/" . $database . "?chamber=" . $chamber . "&amendment_id=" . rawurlencode(strtolower($amendment_id)) . "&apikey=de0223ad155f4783bd1c22edbf49673d";
//                $ch = curl_init();
//                curl_setopt($ch, CURLOPT_URL, $url);
//                curl_setopt($ch, CURLOPT_RETURNTRANSFER, 1);
//                $result = json_decode(curl_exec($ch), true);
//                curl_close($ch);
                $contents = file_get_contents($url);
                $result = json_decode($contents, true);

                $array = $result["results"];
                if (sizeof($array) == 0) {
                    echo '<div id="zero">The API returned zero results for the request.</div>';
                    return;
                }
                echo "<table id='list'><tr><td><b>Amendment ID</b></td><td><b>Amendment Type</b></td><td><b>Chamber</b></td><td><b>Introduced On</b></td></tr>";
                foreach ($array as $ele) {
                    echo '<tr>';
                    echo '<td>' . $ele["amendment_id"] . '</td>';
                    echo '<td>' . $ele["amendment_type"] . '</td>';
                    echo '<td>' . $ele["chamber"] . '</td>';
                    echo '<td>' . $ele["introduced_on"] . '</td>';
                    echo '</tr>';
                }
                echo "</table>";
                echo "</div>";
            }
        }




        function get_detail($data) {
            if ($data["database"] == "legislators") {
                echo "<div id='detail' style='display:none' name='". $data["bioguide_id"] ."'>";
                $database = $data["database"];
                $chamber = $data["chamber"];
                $state = get_state_code($data["state"]);
                $bioguide_id = $data["bioguide_id"];

                $url = "http://congress.api.sunlightfoundation.com/" . $database . "?chamber=" . $chamber . "&state=" . $state . "&bioguide_id=" . $bioguide_id . "&apikey=de0223ad155f4783bd1c22edbf49673d";
//                $ch = curl_init();
//                curl_setopt($ch, CURLOPT_URL, $url);
//                curl_setopt($ch, CURLOPT_RETURNTRANSFER, 1);
//                $result = json_decode(curl_exec($ch), true);
//                curl_close($ch);
                $contents = file_get_contents($url);
                $result = json_decode($contents, true);

                $ele = $result["results"][0];

                $image = "http://theunitedstates.io/images/congress/225x275/" . $bioguide_id . ".jpg";
                $name = $ele["first_name"] . " " . $ele["last_name"];
                $fullname = $ele["title"] . " " . $ele["first_name"] . " " . $ele["last_name"];
                $term = $ele["term_end"];
                $website = $ele["website"];
                $office = $ele["office"];
                $facebook = "http://www.facebook.com/" . $ele["facebook_id"];
                $twitter = "http://twitter.com/" . $ele["twitter_id"];
                echo "</br>";
                echo "<img src='$image'/>";
                echo "<table id='detail'>";
                echo "<tr><td>Full Name</td><td>" . $fullname . "</td></tr>";
                echo "<tr><td>Term Ends on</td><td>" . $term . "</td></tr>";
                if ($website == null)
                    echo "<tr><td>Website</td><td>NA</td></tr>";
                else
                    echo "<tr><td>Website</td><td><a href='$website'>" . $website . "</a></td></tr>";
                if ($office == null)
                    echo "<tr><td>Office</td><td>NA</td></tr>";
                else
                    echo "<tr><td>Office</td><td>" . $office . "</td></tr>";
                if ($ele["facebook_id"] == null)
                    echo "<tr><td>Facebook</td><td>NA</td></tr>";
                else
                    echo "<tr><td>Facebook</td><td><a href='$facebook'>" . $name . "</a></td></tr>";
                if ($ele["twitter_id"] == null)
                    echo "<tr><td>Twitter</td><td>NA</td></tr>";
                else
                    echo "<tr><td>Twitter</td><td><a href='$twitter'>" . $name . "</a></td></tr>";
                echo "</table>";
                echo "</br>";
                echo "</div>";
            }
            else if ($data["database"] == "bills") {
                echo "<div id='detail'>";
                $database = $data["database"];
                $chamber = $data["chamber"];
                $bill_id = trim($data["bill_id"]);

                $url = "http://congress.api.sunlightfoundation.com/" . $database . "?chamber=" . $chamber . "&bill_id=" . strtolower($bill_id) . "&apikey=de0223ad155f4783bd1c22edbf49673d";
//                $ch = curl_init();
//                curl_setopt($ch, CURLOPT_URL, $url);
//                curl_setopt($ch, CURLOPT_RETURNTRANSFER, 1);
//                $result = json_decode(curl_exec($ch), true);
//                curl_close($ch);
                $contents = file_get_contents($url);
                $result = json_decode($contents, true);

                $ele = $result["results"][0];

                $title = $ele["short_title"];
                $sponsor = $ele["sponsor"]["title"] . " " . $ele["sponsor"]["first_name"] . " " . $ele["sponsor"]["last_name"];
                $introduce = $ele["introduced_on"];
                $last_action = $ele["last_version"]["version_name"] . ", " . $ele["last_action_at"];
                $bill_url = $ele["last_version"]["urls"]["pdf"];
                echo "</br>";
                echo "<table id='detail'>";
                echo "<tr><td>Bill ID</td><td>" . strtolower($bill_id) . "</td></tr>";
                if ($title == null)
                    echo "<tr><td>Bill Title</td><td>NA</td></tr>";
                else
                    echo "<tr><td>Bill Title</td><td>" . $title . "</td></tr>";
                if ($sponsor == null)
                    echo "<tr><td>Sponsor</td><td>NA</a></td></tr>";
                else
                    echo "<tr><td>Sponsor</td><td>" . $sponsor . "</a></td></tr>";
                if ($introduce == null)
                    echo "<tr><td>Introduced On</td><td>NA</td></tr>";
                else
                    echo "<tr><td>Introduced On</td><td>" . $introduce . "</td></tr>";
                if ($last_action == null)
                    echo "<tr><td>Last action with data</td><td>NA</td></tr>";
                else
                    echo "<tr><td>Last action with data</td><td>" . $last_action . "</td></tr>";
                if ($title == null)
                    echo "<tr><td>Bill URL</td><td><a href='$bill_url'>NA</a></td></tr>";
                else
                    echo "<tr><td>Bill URL</td><td><a href='$bill_url'>" . $title . "</a></td></tr>";
                echo "</table>";
                echo "</br>";
                echo "</div>";
            }
        }
        ?>

</body>