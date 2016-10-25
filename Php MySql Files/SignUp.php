<?php
mysql_connect("localhost","1247616","shubham78") or die("Connection not Successfull")
mysql_select_db("1247616") or die("Database not found")
if(isset($_POST['Name']) && isset($_POST['Username']) && isset($_POST['Password']) && isset($_POST['Gender']) && isset($_POST['Email']) && isset($_POST['Phone'])){
	$Name = $_POST('Name')
	$Username = $_POST('Username')
	$Password = $_POST('Password')
	$Gender = $_POST('Gender')
	$Email = $_POST('Email')
	$Phone = $_POST('Phone')
	$qry = "Insert into LoginInfo(Name,Username,Password,Gender,Email,Phone) values('$Name','$Username','$Password','$Gender','$Email','$Phone')"
	mysql_query($qry) or die ("Query Problem")
}
else
{
	echo "No data...."
}
?>