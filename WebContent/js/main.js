var curUplVideo;
var totalCount = 0;
var curVideo;
var isUplOpen = false;

var currPageNum = 0;

var currOrder = "by-date-desc";

$(function(){
	
	function factoryCall(num){
		currPageNum = num-1;
		
		switch(currOrder){
		case "by-date-desc":
			getLatestVideos();
			$("#vidListContainer").fadeIn("slow");
			break;
		case "by-rating-desc":
			getBestVideos();
			$("#vidListContainer").fadeIn("slow");
			break;
		case "by-hits-desc":
			getPopularVideos();
			$("#vidListContainer").fadeIn("slow");
			break;
		}

			
		
	}
	
	function rate(id,score){
		$.ajax({
			url:"rest/videos/rate",
			type:"POST",
			//processData: false,
			//contentType:false,
			data:{
				"id":id,
				"score":score
			},
			success:function(out){
				alert("You rated the video: "+score.toString());
			}
		});
	}
	
	
	
	
	$.fn.raty.defaults.path = "img";
	$("#star").raty();
	
	
	
	
	function disableForm(){
		$("input").prop("disabled",true);
	}
	
	function enableForm(){
		$("input").prop("disabled",false);
	}
	
	function clearForm(){
		$("#uplName").val("");
		$("#uplDescr").val("");
		$("#file").val("");
		$("#thumbFile").val("");
	}
	
	function setCurVideo(id){
		$.ajax({
			url:"rest/videos/"+id.toString(),
			type:"GET",
			processData: false,
			contentType:false,
			async:false,
			success:function(video){
				curVideo = video;
				
				curVideo["path"] = curVideo["path"].replace("https://s3.amazonaws.com/newbtube/","http://d3gf292bi7knwd.cloudfront.net/")
				//alert(curVideo["path"]);
			}
		});
	}
	
	function hit(id){
		$.ajax({
			url:"rest/videos/hit/"+id.toString(),
			type:"POST",
			processData: false,
			contentType:false,
			success:function(out){
				curVideo["hits"]+=1;
			}
		});
	}
	
	function getHtmlVideoList(videos){
		var htmlContent = '<ul id="vidList">';
		for(var i=0; i<videos.length;++i){
			var avgRating = videos[i]['possRatings'] == 0? 0 : (videos[i]["ratings"]/videos[i]["possRatings"])*5;
			htmlContent += '<li class="vidListItem" id="'+videos[i]['id']+'">';
			htmlContent += '<a href="#_"><h4>'+videos[i]['name']+'</h4>';
			htmlContent += '<img class="thumbHolder" src="'+videos[i]['thumbPath']+'" width="160px" height="120px"></img></a>';
			htmlContent += '<p><b>Avg Rating: </b>'+avgRating+'</p><p><b>Hits: </b>'+videos[i]["hits"]+'</p>';
			htmlContent += '<hr></hr></li>';
		}
		htmlContent +='</ul>';
		return htmlContent;
	}


	function setPlayer(list){
		
		var id = list.attr("id");
		$("#vidPlayContainer").show();
		setCurVideo(id);
		hit(id);
		
		var rating = curVideo["ratings"];
		var possRating = curVideo["possRatings"];
		
		$("#name").html(curVideo["name"]);
		
		if(possRating == 0){
			$("#star").raty({
				score: 0,
				click: function(score, evt) {
					  //alert(score);
					  rate(curVideo["id"],score);
				  }
			});
		}
		else
		{
			$("#star").raty({
				score: (rating/possRating)*5,
				click: function(score, evt) {
					  //alert(score);
					  rate(curVideo["id"],score);
				  }
			});
		}
		$("#hits").html(curVideo["hits"]);
		_V_("player").src([{src:curVideo["path"],type:"video/mp4"}]);
		$("#playDescr").html(curVideo["descr"]);
		_V_("player").play();
		
		 $("html, body").animate({ scrollTop: 0 }, 1000);
		 
		/* jwplayer("player").setup({
		        file: curVideo["path"],
		        image: curVideo["thumbPath"],
		        width: "550",
		        height: "303.75"
		    });*/
		 
		
	}
	
	function setPagination(){
		$.ajax({
			url:"rest/videos/count",
			type:"GET",
			success:function(out){
				totalCount = out;
				//alert(out);
				$(".paginate").pagination({
			        items: totalCount,
			        itemsOnPage: 5,
			        displayedPages:3,
			        prevText:"<",
			        nextText:">",
			        hrefTextPrefix:"#",
			        onPageClick: function(num,event){
			        		$("#vidListContainer").fadeOut("slow");
			        		factoryCall(num);
			        	},
			        cssStyle: 'compact-theme'
			    });
				
			}
		});
		
		
		
	}
	
	
	function initPlayer(){
		
		$('.vidListItem').on('click',function(){
			setPlayer($(this));
		});
		
		//setPlayer($(".vidListItem").eq(0));
		
	}


	function getLatestVideos(){
		
		$.ajax({
			url:"rest/videos/findbydate/"+currPageNum.toString(),
			type:"GET",
			processData: false,
			contentType:false,
			success:function(out){
				//alert(out);
				//var videos = jQuery.parseJSON(out);
				
				$('#vidListContainer').html(getHtmlVideoList(out));
				
				initPlayer();
			}
		});
	}
	
	function getPopularVideos(){
		$.ajax({
			url:"rest/videos/findbyhitsdesc/"+currPageNum.toString(),
			type:"GET",
			processData: false,
			contentType:false,
			success:function(out){
				//alert(out);
				//var videos = jQuery.parseJSON(out);
				
				$('#vidListContainer').html(getHtmlVideoList(out));
				
				initPlayer();
			}
		});
	}
	
	function getBestVideos(){
		$.ajax({
			url:"rest/videos/findbyratingdesc/"+currPageNum.toString(),
			type:"GET",
			processData: false,
			contentType:false,
			success:function(out){
				//alert(out);
				//var videos = jQuery.parseJSON(out);
				
				$('#vidListContainer').html(getHtmlVideoList(out));
				
				initPlayer();
			}
		});
	}
	
	function updateProgress(pct){
		$( "#progressbar" ).progressbar({
		      value: pct
		    });
	}

	function updateProgressValue(evt){
		
		
		
		if(evt.lengthComputable){
			var pct = Math.round(evt.loaded/evt.total)*100;
			//$("#pct").html("Uploaded "+pct+"%");
			if(pct < 100){
				disableForm();
				updateProgress(pct);
			}
		}
	}
	

	function uploadClick(event){
		disableForm();
		$('.wait').show();
		//$('#progressbar').show();
		if($.trim($('#uplName').val()) != "" && $("#file").val() != ""){
			var input = document.getElementById("file"),formdata=false;
			var thumbInput = document.getElementById("thumbFile");
			var file = input.files[0];
			var isThumbPresent=false;
			var thumbFile;
			if(thumbInput.files.length != 0){
				isThumbPresent = true;
				thumbFile = thumbInput.files[0];
			}
			
			
			
			if(window.FormData){
				formdata = new FormData();
				//alert("new formdata");
			}
			
			
			if(!file.type.match('video.mp4')){
				 displayALertMessage("Only videos allowed", "Please upload only video formats", "error");
				 return;
			}

			if (!!file.type.match(/*.*/)) {
				reader = new FileReader();
				//reader.onerror = errorHandler;
				reader.onprogress = updateProgressValue;
				reader.onabort = function(e) {
					displayALertMessage('File read cancelled', "", "error");
				};
				reader.onloadstart = function(e) {
					updateProgress(0);
				};
				reader.onload = function(e) {

					//updateProgress(100);
				};
				reader.readAsBinaryString(file);
			}
			
			
			if(formdata){
				formdata.append("file",file,file.name);
				formdata.append("name",$('#uplName').val());
				formdata.append("descr",$("#uplDescr").val());
				if(isThumbPresent){
					formdata.append("thumbFile",thumbFile,thumbFile.name);
				}
				else
					formdata.append("thumbFile",null,null);
				
				//alert("added formdata")
			}
			
			$.ajax({
				url: "rest/videos/insertvid",
				type: "POST",
				data: formdata,
				processData:false,
				contentType:false,
				success:function(out){
					//curUplVideo = jQuery.parseJSON(out);
					//uploadVideo(formdata);
					alert("Video Uploaded");
					getLatestVideos();
					clearForm();
					enableForm();
					$('.wait').hide();
					$('#progressbar').hide();
					$("pct").hide();
					clearNavSelection();
					$("#bydate").attr("class","active");
				}
			});
			
		}
		
		else{
			alert("Name and Video file cannot be empty");
			enableForm();
		}
		
		
		
		//alert("this");
	}


	$('#butt').on('click',uploadClick);
	
	$("#uploadToggle").on('click',function(){
		if(!isUplOpen){
			$("#upload").slideDown("slow");
			$("#uploadToggle").attr("class","active");
			isUplOpen=true;
		}
		else{
			$("#upload").slideUp("slow");
			$("#uploadToggle").attr("class","");
			isUplOpen=false;
		}
	});
	
	function deleteVid(id){
		
		var r = confirm("Are you sure you want to delete this video?");
		
		if(r==true){
			$.ajax({
				url:"rest/videos/delete/"+id.toString(),
				type:"DELETE",
				processData: false,
				contentType:false,
				success:function(out){
					alert("Deleted");
					factoryCall(currPageNum+1);
					$("#vidPlayContainer").fadeOut(1000);
					//setPlayer($(".vidListItem").eq(0));
					
				}
			});
		}
		
		
	}
	
	$(".del").on('click',function(){
		
		var id = this.parent().attr("id");
		deleteVid(id);
	});
	
	$(".delPlay").on("click",function(){
		deleteVid(curVideo["id"]);
	});
	
	function clearNavSelection(){
		$("#bydate").attr("class","");
		$("#byrating").attr("class","");
		$("#byhits").attr("class","");
	}
	
	$("#bydate").on("click",function(){
		clearNavSelection();
		$("#vidListContainer").fadeOut("slow");
		$("#bydate").attr("class","active");
		setPagination();
		currOrder = "by-date-desc";
		factoryCall(1);
	});
	
	$("#byrating").on("click",function(){
		clearNavSelection();
		$("#vidListContainer").fadeOut("slow");
		$("#byrating").attr("class","active");
		setPagination();
		currOrder = "by-rating-desc";
		factoryCall(1);
	});
	
	$("#byhits").on("click",function(){
		clearNavSelection();
		$("#vidListContainer").fadeOut("slow");
		$("#byhits").attr("class","active");
		setPagination();
		currOrder = "by-hits-desc";
		factoryCall(1);
	});
	
	
	$('document').ready(function(){
		setPagination();
		factoryCall(1);
		clearNavSelection();
		$("#bydate").attr("class","active");
		$("#upload").hide();
		$(".wait").hide();
		$("#progressbar").hide();
		$("#pct").hide();
		$("#vidPlayContainer").hide();
	});
	
});












