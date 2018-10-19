"use strict";

(function ($) {
  $(document).on('change', '.file-field input[type="file"]', function (e) {
    let $this = $(e.target);
    let $fileField = $this.closest('.file-field');
    let $pathInput = $fileField.find('input.file-path');
    let files = $this[0].files;
    let fileNames = []; // files.forEach((file) => fileNames.push(file.name));

    if (Array.isArray(files)) {
      files.forEach(function (file) {
        return fileNames.push(file.name);
      });
    } else {
      Object.keys(files).forEach(function (key) {
        fileNames.push(files[key].name);
      });
    }

    $pathInput.val(fileNames.join(', '));
    $pathInput.trigger('change');
  });
})(jQuery);