"use strict";

(function ($) {
  $.fn.characterCounter = function () {
    return this.each(function () {
      let itHasLengthAttribute = $(this).attr('length') !== undefined;

      if (itHasLengthAttribute) {
        $(this).on('input', updateCounter);
        $(this).on('focus', updateCounter);
        $(this).on('blur', removeCounterElement);
        addCounterElement($(this));
      }
    });
  };

  function updateCounter() {
    let maxLength = Number($(this).attr('length'));
    let actualLength = Number($(this).val().length);
    let isValidLength = actualLength <= maxLength;
    $(this).parent().find('span[class="character-counter"]').html(`${actualLength}/${maxLength}`);
    addInputStyle(isValidLength, $(this));
  }

  function addCounterElement($input) {
    let $counterElement = $('<span/>').addClass('character-counter').css('float', 'right').css('font-size', '12px').css('height', 1);
    $input.parent().append($counterElement);
  }

  function removeCounterElement() {
    $(this).parent().find('span[class="character-counter"]').html('');
  }

  function addInputStyle(isValidLength, $input) {
    let inputHasInvalidClass = $input.hasClass('invalid');

    if (isValidLength && inputHasInvalidClass) {
      $input.removeClass('invalid');
    } else if (!isValidLength && !inputHasInvalidClass) {
      $input.removeClass('valid');
      $input.addClass('invalid');
    }
  }

  $(document).ready(function () {
    $('input, textarea').characterCounter();
  });
})(jQuery);