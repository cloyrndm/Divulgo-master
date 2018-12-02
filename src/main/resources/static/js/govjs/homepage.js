$(document).ready(function() {
    $('#example').DataTable(

        {

            "aLengthMenu": [[5, 10, 25, -1], [5, 10, 25, "All"]],
            "iDisplayLength": 5
        }
    );
} );


function checkAll(bx) {
    var cbs = document.getElementsByTagName('input');
    for(var i=0; i < cbs.length; i++) {
        if(cbs[i].type == 'checkbox') {
            cbs[i].checked = bx.checked;
        }
    }
}

//new
$(window).resize(function() {
    //update stuff
});

//Change pos/background/padding/add shadow on nav when scroll event happens
$(function(){
    var navbar = $('.navbar');
    var navDropdown = $('.dropdown-menu');

    $(window).scroll(function(){
        if($(window).scrollTop() <= 40){
            navbar.removeClass('navbar-scroll');
            navDropdown.removeClass('nav-dropdown-scroll');
            $('.top').hide();
        } else {
            navbar.addClass('navbar-scroll');
            navDropdown.addClass('nav-dropdown-scroll');
            $('.top').show();
        }
    });
    $('.navbar-toggle').click(function(){
        if($(window).scrollTop() <= 40){
            navbar.addClass('navbar-scroll');
        }
    });
});


//Close collapse nav when scroll spy page link is clicked
$('.navbar-nav a[href*="#spy"]').click(function(){
    $('.navbar-collapse').collapse('hide');
    if($(window).scrollTop() <= 40){
        $('.navbar').removeClass('navbar-scroll');
    }
});


//Get height of col next to img col and apply a fixed height for flexbox to work correctly.
$(function(){
    var flexColHeight = $('.to-match').height();
    var flexCol = $('.css-img-wrapper');

    flexCol.css('height', flexColHeight);
});


//Smooth Scrolling For Internal Page Links
$(function() {
    $('a[href*=#spy]:not([href=#])').click(function() {
        if (location.pathname.replace(/^\//,'') == this.pathname.replace(/^\//,'') && location.hostname == this.hostname) {
            var target = $(this.hash);
            target = target.length ? target : $('[name=' + this.hash.slice(1) +']');
            if (target.length) {
                $('html,body').animate({
                    scrollTop: target.offset().top
                }, 1000);
                return false;
            }
        }
    });
});

//send
// erase the input
$("#erase").click(function(){
    $(this).prev().val("");
});

$("#erase1").click(function(){
    $(this).prev().val("");
});

// preview and hide password
$("#showPassword").click(function(){
    var foo = $(this).prev().attr("type");
    if(foo == "password"){
        $(this).prev().attr("type", "text");
    } else {
        $(this).prev().attr("type", "password");
    }
});

//show help
$("#showHelp").click(function(){
    $("#help").toggle();
});

//<script type = "text/javascript">
//function pic()
//{
//    document.getElementById("img").src = "picture 1 source";
//}
