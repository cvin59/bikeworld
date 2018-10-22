"use strict";

(function ($) {
  $(document).on('click.card', '.card', function (e) {
    let $reveal = $(this).find('.card-reveal');

    if ($reveal.length) {
      let $clicked = $(e.target);
      let isTitle = $clicked.is('.card-reveal .card-title');
      let isTitleIcon = $clicked.is('.card-reveal .card-title i');
      let isActivator = $clicked.is('.card .activator');
      let isActivatorIcon = $clicked.is('.card .activator i');

      if (isTitle || isTitleIcon) {
        // down
        $(this).find('.card-reveal').velocity({
          translateY: 0
        }, {
          duration: 225,
          queue: false,
          easing: 'easeInOutQuad',
          complete: function complete() {
            $(this).css({
              display: 'none'
            });
          }
        });
      } else if (isActivator || isActivatorIcon) {
        // up
        $(this).find('.card-reveal').css({
          display: 'block'
        }).velocity('stop', false).velocity({
          translateY: '-100%'
        }, {
          duration: 300,
          queue: false,
          easing: 'easeInOutQuad'
        });
      }
    }
  });
  $('.rotate-btn').on('click', function () {
    let cardId = $(this).attr('data-card');
    $(`#${cardId}`).toggleClass('flipped');
  });
  var frontHeight = $('.front').outerHeight();
  var backHeight = $('.back').outerHeight();

  if (frontHeight > backHeight) {
    $('.card-wrapper, .back').height(frontHeight);
  } else if (frontHeight > backHeight) {
    $('.card-wrapper, .front').height(backHeight);
  } else {
    $('.card-wrapper').height(backHeight);
  }

  $('.card-share > a').on('click', function (e) {
    e.preventDefault();
    $(this).toggleClass('share-expanded').parent().find('div').toggleClass('social-reveal-active');
  });
})(jQuery);