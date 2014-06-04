<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
<!DOCTYPE html >
<html>
 <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Register Device</title>

    <!-- Bootstrap -->
    <link href="css/bootstrap.min.css" rel="stylesheet">
 
    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
      <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

  </head>
<body>
 
    <!-- Include all compiled plugins (below), or include individual files as needed -->

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
        <script src="js/bootstrap.min.js"></script>
        <script src="http://ajax.aspnetcdn.com/ajax/jquery.validate/1.11.1/jquery.validate.js"></script>
    



<h1>Register Device</h1>

<form class="form-horizontal" method="post" id="contact-form" action="http://localhost:8080/t3v2/1/device/register">
  <div class="form-group">
    <label for="dev_id" class="col-sm-2 control-label">Your Device Identifier</label>
    <div class="col-sm-10">
    <div class="input-group">
            <span class="input-group-addon"></span>
      <input type="text" class="form-control" id="dev_name" name="dev_id" placeholder="e.g simbox001">
    </div></div>
  </div>
  
  <div class="form-group">
    <label for="dev_name" class="col-sm-2 control-label">Name for Device</label>
    <div class="col-sm-10">
    <div class="input-group">
            <span class="input-group-addon"></span>
      <input type="text" class="form-control" id="dev_name" name="dev_name" placeholder="Name for Device">
    </div></div>
  </div>
  <div class="form-group">
    <label for="dev_img" class="col-sm-2 control-label">Picture URL</label> 
    <div class="col-sm-10">
    <div class="input-group">
            <span class="input-group-addon"></span>
      <input type="url" class="form-control" id="dev_img" name="dev_img" placeholder="http://path/to/image.jpg">
    </div></div>
  </div>
  
  <div class="form-group">
    <label for="dev_desc" class="col-sm-2 control-label">Device Description</label> 
    <div class="col-sm-10">
    <div class="input-group">
            <span class="input-group-addon"></span>
      <input type="text" class="form-control"name="dev_desc" id="dev_desc" placeholder="Description of the IoT device">
    </div></div>
  </div>
   <div class="form-group">
    <label for="dev_desc" class="col-sm-2 control-label">Security Description</label>
 
    <div class="col-sm-10">
    <div class="input-group">
            <span class="input-group-addon"></span>
      <input type="text" class="form-control" name="dev_sec_desc" id="dev_sec_desc" placeholder="Security description of the IoT device">
    </div></div>
  </div>
  
    <div class="form-group">
    <label for="dev_type" class="col-sm-2 control-label">Device Type</label>
    <div class="col-sm-10">
    <div class="input-group">
            <span class="input-group-addon"></span>
      <input type="text" class="form-control" id="dev_type" name="dev_type" placeholder="Telematics Box">
    </div></div>
  </div>
  
  <div class="form-group">
  <h2>Owner</h2>
    <label for="dev_own_name" class="col-sm-2 control-label">Company Name</label>

    <div class="col-sm-10">
    <div class="input-group">
            <span class="input-group-addon"></span>
      <input type="text" class="form-control" id="dev_own_name" name="dev_own_name" placeholder="Full Company Name">
    </div></div>
     <label for="dev_own_address" class="col-sm-2 control-label">Address</label>

    <div class="col-sm-10">
    <div class="input-group">
            <span class="input-group-addon"></span>
      <input type="text" class="form-control" id="dev_own_address" name="dev_own_address" placeholder="Address">
    </div></div>
     <label for="dev_own_email" class="col-sm-2 control-label">Email</label>
    <div class="col-sm-10">
      <div class="input-group">
            <span class="input-group-addon"></span>
      <input type="email" class="form-control" id="dev_own_email" name="dev_own_email" placeholder="joe@bloggs.com">
    </div></div>
     <label for="dev_own_logo" class="col-sm-2 control-label">Logo</label>

    <div class="col-sm-10">
    <div class="input-group">
            <span class="input-group-addon"></span>
      <input type="url" class="form-control" id="dev_own_logo" name="dev_own_logo" placeholder="Url for comp logo">
    </div></div>
     <label for="dev_own_tel" class="col-sm-2 control-label">Phone Number</label>

    <div class="col-sm-10">
    <div class="input-group">
            <span class="input-group-addon"></span>
      <input type="tel" class="form-control" id="dev_own_tel" name="dev_own_tel" placeholder="Contact Number">
    </div></div>
     <label for="dev_own_web" class="col-sm-2 control-label">Website</label>
     
    <div class="col-sm-10">
     <div class="input-group">
            <span class="input-group-addon"></span>
      <input type="url" class="form-control" name="dev_own_web"id="dev_own_web" placeholder="Company Website">
      </div>
    </div>
  </div>
  
   <div class="form-group">
  <h2>Manufacturer</h2>
    <label for="dev_man_name" class="col-sm-2 control-label">Company Name</label>

    <div class="col-sm-10">
         <div class="input-group">
            <span class="input-group-addon"></span>
      <input type="text" class="form-control" name="dev_man_name" id="dev_man_name" placeholder="Full Company Name">
    </div></div>
     <label for="dev_man_address" class="col-sm-2 control-label">Address</label>
    <div class="col-sm-10">
         <div class="input-group">
            <span class="input-group-addon"></span>
      <input type="text" class="form-control" id="dev_man_address" name="dev_man_address" placeholder="Address">
    </div></div>
     <label for="dev_man_logo" class="col-sm-2 control-label">Logo</label>
    <div class="col-sm-10">
      <div class="input-group">
            <span class="input-group-addon"></span>
      <input type="url" class="form-control" id="dev_man_logo" name="dev_man_logo" placeholder="Url for comp logo">
    </div></div>
    <label for="dev_man_email" class="col-sm-2 control-label">Email</label>
    <div class="col-sm-10">
      <div class="input-group">
            <span class="input-group-addon"></span>
      <input type="email" class="form-control" id="dev_man_email" name="dev_man_email" placeholder="joe@bloggs.com">
    </div></div>
     <label for="dev_man_tel" class="col-sm-2 control-label">Phone Number</label>
    <div class="col-sm-10">
      <div class="input-group">
            <span class="input-group-addon"></span>
      <input type="tel" class="form-control" id="dev_man_tel" name="dev_man_tel" placeholder="Contact Number">
    </div></div>
     <label for="dev_man_web" class="col-sm-2 control-label">Website</label>
    <div class="col-sm-10">
        <div class="input-group">
            <span class="input-group-addon"></span>
      <input type="url" class="form-control" id="dev_man_web" name="dev_man_web" placeholder="Company Website">
      </div>
    </div>
  </div>
  

  <div class="form-group">
    <div class="col-sm-offset-2 col-sm-10">
        <div class="input-group">
            <span class="input-group-addon"></span>
      <div class="checkbox">
        <label>
         <input type="checkbox" name="accept" id="accept"> I understand that data provided will be publicly available.
        </label>
      </div></div>
    </div>
  </div>
  <div class="form-group">
    <div class="col-sm-offset-2 col-sm-10">
      <button type="submit" class="btn btn-default">Register Device</button>
    </div>
  </div>
</form>


</body>
</html>