<!DOCTYPE html  [
	<!ENTITY nbsp   "&#160;">
	<!ENTITY copy   "&#169;">
	<!ENTITY reg    "&#174;">
	<!ENTITY trade  "&#8482;">
	<!ENTITY mdash  "&#8212;">
	<!ENTITY ldquo  "&#8220;">
	<!ENTITY rdquo  "&#8221;"> 
	<!ENTITY pound  "&#163;">
	<!ENTITY yen    "&#165;">
	<!ENTITY euro   "&#8364;">
]>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:output method="html" encoding="UTF-8" doctype-public="-//W3C//DTD XHTML 1.0 Transitional//EN" doctype-system="http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"/>
<xsl:template match="/">


<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  	<meta charset="utf-8"/>
  	<title>London Attractions</title>
  	<meta name="viewport" content="width=device-width, initial-scale=1"/>
  	<link rel="stylesheet" href="http://code.jquery.com/mobile/1.4.5/jquery.mobile-1.4.5.min.css"/> 
	<style>

		#header{
			font-family:"century gothic", "Times New Roman";
		
		}
		#uMap,#mapSize{ 
			width: 100%; 
			height: 80%; 
			padding: 0; 
		}
		#lsP,#mapSize{ 
			width: 100%; 
			height: 100%; 
			padding: 0; 
		}
	</style>
  	<script src="http://code.jquery.com/jquery-1.10.2.min.js"></script> 
  	<script src="http://code.jquery.com/mobile/1.4.5/jquery.mobile-1.4.5.min.js"></script>
	<script src="http://maps.google.com/maps/api/js?sensor=false"></script>
	<script>

	/*
	 * Google Maps documentation: http://code.google.com/apis/maps/documentation/javascript/basics.html
	 * Geolocation documentation: http://dev.w3.org/geo/api/spec-source.html
	 */

	$( document ).on( "pagecreate", "#uMap", function() {
		var defaultLatLng = new google.maps.LatLng(51.5072, 0.1275);  

		if ( navigator.geolocation ) {
			function success(pos) {
				drawMap(new google.maps.LatLng(pos.coords.latitude, pos.coords.longitude));
			}

			function fail(error) {
				
				
				drawMap(defaultLatLng);
			}

			   navigator.geolocation.getCurrentPosition(success, fail, {maximumAge: 500000, enableHighAccuracy:true, timeout: 6000});
		} else {
			drawMap(defaultLatLng);  
		}

		
		function drawMap(latlng) {
			var myOptions = {
				zoom: 10,
				center: latlng,
				mapTypeId: google.maps.MapTypeId.ROADMAP
			};

			var map = new google.maps.Map(document.getElementById("mapSize"), myOptions);
			var marker = new google.maps.Marker({
				position: latlng,
				map: map,
				title: "You are Here"
			});	

		}

	});
	
	</script>
</head>
<body>

	<div data-role="page" data-theme="b" >
		<div data-role="header" id="header"> <h1>London Attractions</h1></div>
		<div data-role="content">
		<ul data-role="listview" data-inset="true">
  		<xsl:for-each select="lAttractions/attractions">
		<li><a><xsl:attribute name="href">#<xsl:value-of select="id"/></xsl:attribute>><img><xsl:attribute name="src">
                 			<xsl:value-of select="pictureS"/>
                  			</xsl:attribute>
                  			</img>
					<xsl:value-of select="name"/>
					<p><xsl:value-of select="sDescription"/></p>
					<p><xsl:value-of select="rating"/></p></a> </li>
  		</xsl:for-each>     
		</ul>
		<a href="#uMap" data-ajax="false" class="ui-btn ui-corner-all ui-btn-inline ui-icon-arrow-r ui-btn-icon-right">User Location</a>
		</div>
		
	</div><!-- /page -->
		<xsl:for-each select="lAttractions/attractions">
		<div data-role="page" data-theme="b"><xsl:attribute name="id"><xsl:value-of select="id"/></xsl:attribute>
			<div data-role="header" data-add-back-btn = "true" data-back-btn-text="Home" id="header"> <h1><xsl:value-of select="name"/></h1></div>
			<div data-role="content" id="content">
					<img style = "width:100%; height:56%">
                 			<xsl:attribute name="src">
                 			<xsl:value-of select="picture"/>
                  			</xsl:attribute>
                  			</img>
				
					<div data-role="collapsible">
   						<h4>Address</h4>
    						<xsl:value-of select="address"/>
					</div>
					<div data-role="collapsible">
   						<h4>Underground Station</h4>
    						<xsl:value-of select="tube"/>
					</div>
					<div data-role="collapsible">
   						<h4>Description</h4>
    						<xsl:value-of select="lDescription"/>
					</div>
					<div data-role="collapsible">
   						<h4>Open Hours</h4>
    						<xsl:value-of select="opening"/>
					</div>
					<div data-role="collapsible">
   						<h4>Prices</h4>
    						<xsl:value-of select="price"/>
					</div>
					<div data-role="collapsible">
   						<h4>Contact Number</h4>
    						<xsl:value-of select="phone"/>
					</div>
					<div data-role="collapsible">
   						<h4>Category</h4>
    						<xsl:value-of select="category"/>
					</div>
					<div data-role="collapsible">
   						<h4>Fun Fact</h4>
    						<xsl:value-of select="funFact"/>
					</div>
					
					
					
			</div>


		</div>
		</xsl:for-each><!-- /page -->
		
		<div data-role="page" id="uMap" data-url="uMap" data-theme="b">
   			<div data-role="header" data-add-back-btn = "true" data-back-btn-text="Home">
    			<h1>Your Location</h1>
   		</div>
    		<div role="main" class="ui-content" id="mapSize">
    		</div>
		</div>
		
		
	



</body>
</html>

</xsl:template>
</xsl:stylesheet>