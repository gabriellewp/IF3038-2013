<?php

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
function getalltask(){
    
     $con=mysqli_connect("localhost","progin","progin","progin");
    if (mysqli_connect_errno($con))
        {
            echo "Failed to connect to MySQL: " . mysqli_connect_error();
        }
 // echo "udah ampe sini";
     $sql = "SELECT * FROM task";
    if (!mysqli_query($con,$sql))
        {
             die('Error: ' . mysqli_error());
        }
    $result = mysqli_query($con, $sql);
    $res = array();
    $i = 0;
        // echo "<html><body>";


    while ($row = mysqli_fetch_array($result)) {
            //echo "row ".$i;
            // print_r($row);
            array_push($res, $row);
            //echo "<br/>";
            $i++;
    }
       /* foreach ($res as $row) {
            echo($row['KATEGORI_TASK']);
        }*/
   return $res;  
    
}
 

//$a= get_allategoriphp();


?>
