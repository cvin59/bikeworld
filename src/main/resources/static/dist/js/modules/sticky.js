"use strict";

(function ($) {
  let DEFAULT_TOP_SPACING = 0;

  class Sticky {
    constructor(element, options) {
      this.defaults = {
        topSpacing: DEFAULT_TOP_SPACING,
        zIndex: false,
        stopper: '.sticky-stopper',
        stickyClass: false,
        startScrolling: 'top',
        // can be also 'bottom'
        minWidth: false
      };
      this.$element = element;
      this.options = this.assignOptions(options);
      this.$window = $(window);
      this.$stopper = this.options.stopper;
      this.$stopPoint = this.options.stopper;

      this._getPushPoint();

      this.bindEvents();
    }

    assignOptions(newOptions) {
      return $.extend({}, this.defaults, newOptions);
    }

    bindEvents() {
      this.$window.on('scroll resize', this.init.bind(this));
    }

    hasZIndex() {
      return typeof this.options.zIndex == 'number' ? true : false;
    }

    hasStopper() {
      return $(this.options.stopper).length || typeof this.options.stopper === 'number' ? true : false;
    }

    _getStopperPosition() {
      if (typeof this.options.stopper === 'string') {
        this.$stopPoint = $(this.$stopper).offset().top - this.$element.outerHeight() - this.options.topSpacing;
      }
    }

    _getPushPoint() {
      if (this.options.startScrolling === 'bottom') {
        this.$pushPoint = this.$element.offset().top + this.$element.outerHeight() - this.$window.innerHeight();
        console.log("we'll use this one", this.$pushPoint);
      } else {
        this.$pushPoint = this.$element.offset().top - this.options.topSpacing;
        console.log("and we use this", this.$pushPoint);
      }
    }

    init() {
      if (this.options.minWidth && this.options.minWidth > this.$window.innerWidth()) {
        return false;
      }

      let scrollTop = this.$window.scrollTop();

      if (this.$pushPoint < scrollTop) {
        this._stickyStart();
      } else {
        this._stickyEnd();
      }
    }

    _stickyStart() {
      if (this.options.stickyClass) {
        this.$element.addClass(this.options.stickyClass);
      }

      this.$element.css({
        position: 'sticky',
        top: this.options.topSpacing
      });

      if (this.hasZIndex()) {
        this.$element.css({
          zIndex: this.options.zIndex
        });
      }
    }

    _stickyEnd() {
      if (this.options.stickyClass) {
        this.$element.removeClass(this.options.stickyClass);
      }

      this.$element.css({
        position: 'static',
        top: DEFAULT_TOP_SPACING
      });
    }

    _stop() {
      if (typeof this.options.stopper === 'string') {
        if (scrollTop >= this.$stopPoint) {
          let diff = this.$stopPoint - scrollTop;
          this.$element.css({
            top: diff
          });
        }
      }
    }

  }

  $.fn.sticky = function (options) {
    return this.each(function () {
      new Sticky($(this), options);
    });
  };
})(jQuery);