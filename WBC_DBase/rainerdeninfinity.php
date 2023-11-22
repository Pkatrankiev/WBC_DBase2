<?php
session_start();

$maxValueRain = 0;
$firsMail =  'pkatrankiev@gmail.com'; 
$firsUserMail =   'P.Katrankiev'; 

$secondMail =   'del61@abv.bg'; 
$secondUserMail =   'D.Damianov'; 
 
$subjectMail =   'предупреждение за валежи'; 
$subjectMail = '=?utf-8?B?'.base64_encode($subjectMail).'?=';

$str = 'Предупреждение за прогнозирани валежи в района на с. Ерден от accuweather.com над '.$maxValueRain.'mm по часове';
// $str = '=?utf-8?B?'.base64_encode($str).'?=';

header('Access-Control-Allow-Origin: *');
header("Access-Control-Allow-Headers: X-API-KEY, Origin, X-Requested-With, Content-Type, Accept, Access-Control-Request-Method");
header("Access-Control-Allow-Methods: GET, POST, OPTIONS, PUT, DELETE");
header("Allow: GET, POST, OPTIONS, PUT, DELETE");

if ($_SERVER["REQUEST_METHOD"] == "POST") {
   
    echo $_POST;
}

    $api_key = "PG6Mz0HuIHtF6A42yAPyUPSPhupwoj5d" ;// KAT-BOOK@ABV.BG
// $api_key="YZGFYaJFuJcGT5QtjuOOUKl1PLra2vDn";  //TAPOTIATA@ABV.BG.
// $api_key="LsdmChyvZM5G0qhEyVJGYoDgHvAdaATU";  //accuweatherkozlodui@gmail.com
// echo "123";
$url ="https://dataservice.accuweather.com/forecasts/v1/hourly/12hour/44456?apikey=$api_key&language=bg&details=true&metric=true";

$json = file_get_contents($url);
$obj_json = json_decode(html_entity_decode($json),true);
echo $json;
//Vemos si hay algun error
$json_errors = array(
JSON_ERROR_NONE => 'No error has occurred',
JSON_ERROR_DEPTH => 'The maximum stack depth has been exceeded',
JSON_ERROR_CTRL_CHAR => 'Control character error, possibly incorrectly encoded',
JSON_ERROR_SYNTAX => 'Syntax error',
);


// generateMasiveDataWeather
	$fl =0;
	$flEmptiTextMail = 0;
	$mailText ='';
	for ($i = 0; $i < count($obj_json); $i++) {
			$jsonObject = $obj_json[$i];

//              Data/Time
			$dateTime = $jsonObject['DateTime'];
			$index = strpos($dateTime, 'T');
			$date_str = substr($dateTime, 0, $index);
			$time = substr($dateTime, $index + 1, 2);
			
			$date = new DateTime($date_str);
			
			$hour = intval($time);
			
//                 dajd
			$rain = $jsonObject['Rain'];
			$rainValue = $rain['Value'];
			$rainUnut = $rain['Unit'];
			$rainAllValue = $rainValue. ' ' .$rainUnut;
			

			if($hour===0){
			$fl = 1;
			}
			
			if($fl===1){
// 			$date -> modify('0 day');
			$date_str = $date->format('Y-m-d');
			}
			
			$dataWeather[$i][0] = $date_str;
			$dataWeather[$i][1] = $time;
			$dataWeather[$i][2] = $rainAllValue;
			
			if( floatval($rainValue)>$maxValueRain){
			$mailText .= $date_str.' '.$time.':00 - '.$rainAllValue."<br>\n";
			}
		}
			if(!empty($mailText)){
            echo $mailText."<br>\n";
           	$flEmptiTextMail = 1;
           	
           	$mailText = $str."<br>\n".$mailText;
    	    $_SESSION['mtext'] = $mailText;
            
    		$_SESSION['firsMail'] = $firsMail;
    		$_SESSION['firsUserMail'] = $firsUserMail;
    		$_SESSION['secondMail'] = $secondMail; 
    		$_SESSION['secondUserMail'] = $secondUserMail; 
    		$_SESSION['subjectMail'] = $subjectMail; 
			}
 // insertDataToAccessDBase
 
//         $servername = 'sql106.infinityfree.com';
// 		$username = 'if0_35422371';
// 		$password = 'SNN865lg2eH1o';
// 		$dbname = 'if0_35422371_accuweatherdbase';

		$servername = '127.0.0.1';
		$username = 'root';
		$password = 'root';
		$dbname = 'id19812479_accuweatherdbase';
		
		
// echo "conect2in";
        // Create connection
        $conn = new mysqli($servername, $username, $password, $dbname);
        
        // Check connection
        if ($conn->connect_error) {
            die("Connection failed: " . $conn->connect_error);
        }  else {
            echo " |connection successs <br>";
        }
		// echo "db conectin";
		for ($i = 0; $i < 12; $i++) {
		
		$date = $dataWeather[$i][0];
		
		$sqlGet =	"SELECT * FROM RainErden WHERE Date = '$date'";
		// Check si date to baza
echo " result " . "<br>";
        $result = $conn->query($sqlGet);
echo " result2 " . "<br>";
        
		if (!$result) {
			echo "Sorry, the website is experiencing problems.";
			exit;
		}else{
            echo "wwwwwwwww888";
        }
       $tt = 'n';
        echo "exit".  "<br>";
		if(mysqli_num_rows($result) === 0){
		    
		    mysqli_report(MYSQLI_REPORT_ERROR | MYSQLI_REPORT_STRICT);
		    $mysql = new mysqli($servername, $username, $password, $dbname);
		    
// 		    $date_str = $date->format('Ymd');
		    
// 		    date_default_timezone_set("Europa/Sofia");
// 		    $date = date("dmY");
		    
// 		    $date_str = $date->format('Y-m-d');
		    
		    /* Prepared statement, stage 1: prepare */
		    $stmt = $mysql->prepare('INSERT INTO rainerden (Date, Aur00) VALUES (?,?)');
		    
		    /* Prepared statement, stage 2: bind and execute */
		    $stmt->bind_param( "ss", $date_str ,  $tt); // "is" means that $id is bound as an integer and $label as a string
		    
// 		    $stmt->execute();
		    
		    
		    
		    
		    
		    
		    
		    
		    
// 			// nqma takava date i syzdavame nov red
// 				$sqlinsert = "INSERT INTO rainerden (Date, A0ur, A1ur, 02Hоur, 03Hоur, 04Hоur, 05Hоur, 06Hоur, 07Hоur, 08Hоur, 09Hоur, 
// 				10Hоur, 11Hоur, 12Hоur, 13Hоur, 14Hоur, 15Hоur, 16Hоur, 17Hоur, 18Hоur, 19Hоur, 20Hоur, 21Hоur, 22Hоur, 23Hоur )
// 				VALUES ('".$date."', 'n', 'n', 'n', 'n', 'n', 'n', 'n', 'n', 'n', 'n', 'n', 
// 				'n', 'n', 'n', 'n', 'n', 'n', 'n', 'n', 'n', 'n', 'n', 'n', 'n' )";
				
// echo $sqlinsert . "<br>";
echo"3454321";
//         $result = $conn->query($sqlinsert);
        $result = $stmt->execute();
            echo "result: " ;
            if ($result === TRUE) {
                // echo json_encode($dataWeather);
                echo "1ОК insert new row";
            } 

            else {
            
                echo  "Error: " . $sqlinsert . "<br>" . $conn->error;
            }
            echo " 345433 ";
        }
        $colum_name = 'Aur'.$dataWeather[$i][1];
        $column_value = $dataWeather[$i][2];
        $sqlupdate ="UPDATE RainErden SET ".$colum_name."= '$column_value' WHERE Date = '$date'";
        echo $date.' '.$colum_name.' '.$column_value."<br>\n";	
        
        
        if ($conn->query($sqlupdate) === TRUE) {
            // echo json_encode($dataWeather);
            echo "2ОК ";
                    } 
        else {
           echo  "Error: " . $sqlupdate . "<br>" . $conn->error;
            // echo  "Error: " . $sql . "<br>" . $conn->error;
        }
    
	}
        $conn->close();
        
	if($flEmptiTextMail>0){
	include('sendemail.php');
	}
		
?>