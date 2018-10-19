"use strict";

(function ($) {
  let MENU_WIDTH = 240;
  let SN_BREAKPOINT = 1440;
  let MENU_WIDTH_HALF = 2;
  let MENU_LEFT_MIN_BORDER = 0.3;
  let MENU_LEFT_MAX_BORDER = -0.5;
  let MENU_RIGHT_MIN_BORDER = -0.3;
  let MENU_RIGHT_MAX_BORDER = 0.5;
  let MENU_VELOCITY_OFFSET = 10;

  class SideNav {
    constructor(element, options) {
      this.defaults = {
        MENU_WIDTH,
        edge: 'left',
        closeOnClick: false
      };
      this.$element = element;
      this.options = this.assignOptions(options);
      this.menuOut = false;
      this.$body = $('body');
      this.$menu = $(`#${this.$element.attr('data-activates')}`);
      this.$sidenavOverlay = $('#sidenav-overlay');
      this.$dragTarget = $('<div class="drag-target"></div>');
      this.$body.append(this.$dragTarget);
      this.init();
    }

    init() {
      this.setMenuWidth();
      this.setMenuTranslation();
      this.closeOnClick();
      this.openOnClick();
      this.bindTouchEvents();
    }

    bindTouchEvents() {
      var _this = this;

      this.$dragTarget.on('click', function () {
        _this.removeMenu();
      });
      this.$dragTarget.hammer({
        prevent_default: false
      }).bind('pan', this.panEventHandler.bind(this)).bind('panend', this.panendEventHandler.bind(this));
    }

    panEventHandler(e) {
      if (e.gesture.pointerType !== 'touch') {
        return;
      }

      let touchX = e.gesture.center.x;
      this.disableScrolling();
      let overlayExists = this.$sidenavOverlay.length !== 0;

      if (!overlayExists) {
        this.buildSidenavOverlay();
      } // Keep within boundaries


      if (this.options.edge === 'left') {
        if (touchX > this.options.MENU_WIDTH) {
          touchX = this.options.MENU_WIDTH;
        } else if (touchX < 0) {
          touchX = 0;
        }
      }

      this.translateSidenavX(touchX);
      this.updateOverlayOpacity(touchX);
    }

    translateSidenavX(touchX) {
      if (this.options.edge === 'left') {
        let isRightDirection = touchX >= this.options.MENU_WIDTH / MENU_WIDTH_HALF;
        this.menuOut = isRightDirection;
        this.$menu.css('transform', `translateX(${touchX - this.options.MENU_WIDTH}px)`);
      } else {
        let isLeftDirection = touchX < window.innerWidth - this.options.MENU_WIDTH / MENU_WIDTH_HALF;
        this.menuOut = isLeftDirection;
        let rightPos = touchX - this.options.MENU_WIDTH / MENU_WIDTH_HALF;

        if (rightPos < 0) {
          rightPos = 0;
        }

        this.$menu.css('transform', `translateX(${rightPos}px)`);
      }
    }

    updateOverlayOpacity(touchX) {
      let overlayPercentage;

      if (this.options.edge === 'left') {
        overlayPercentage = touchX / this.options.MENU_WIDTH;
      } else {
        overlayPercentage = Math.abs((touchX - window.innerWidth) / this.options.MENU_WIDTH);
      }

      this.$sidenavOverlay.velocity({
        opacity: overlayPercentage
      }, {
        duration: 10,
        queue: false,
        easing: 'easeOutQuad'
      });
    }

    buildSidenavOverlay() {
      var _this2 = this;

      this.$sidenavOverlay = $('<div id="sidenav-overlay"></div>');
      this.$sidenavOverlay.css('opacity', 0).on('click', function () {
        _this2.removeMenu();
      });
      this.$body.append(this.$sidenavOverlay);
    }

    disableScrolling() {
      let oldWidth = this.$body.innerWidth();
      this.$body.css('overflow', 'hidden');
      this.$body.width(oldWidth);
    }

    panendEventHandler(e) {
      if (e.gesture.pointerType !== 'touch') {
        return;
      }

      let velocityX = e.gesture.velocityX;
      let touchX = e.gesture.center.x;
      let leftPos = touchX - this.options.MENU_WIDTH;
      let rightPos = touchX - this.options.MENU_WIDTH / MENU_WIDTH_HALF;

      if (leftPos > 0) {
        leftPos = 0;
      }

      if (rightPos < 0) {
        rightPos = 0;
      }

      if (this.options.edge === 'left') {
        // If velocityX <= 0.3 then the user is flinging the menu closed so ignore this.menuOut
        if (this.menuOut && velocityX <= MENU_LEFT_MIN_BORDER || velocityX < MENU_LEFT_MAX_BORDER) {
          if (leftPos !== 0) {
            this.translateMenuX([0, leftPos], '300');
          }

          this.showSidenavOverlay();
        } else if (!this.menuOut || velocityX > MENU_LEFT_MIN_BORDER) {
          this.enableScrolling();
          this.translateMenuX([-1 * this.options.MENU_WIDTH - MENU_VELOCITY_OFFSET, leftPos], '200');
          this.hideSidenavOverlay();
        }

        this.$dragTarget.css({
          width: '10px',
          right: '',
          left: 0
        });
      } else if (this.menuOut && velocityX >= MENU_RIGHT_MIN_BORDER || velocityX > MENU_RIGHT_MAX_BORDER) {
        this.translateMenuX([0, rightPos], '300');
        this.showSidenavOverlay();
        this.$dragTarget.css({
          width: '50%',
          right: '',
          left: 0
        });
      } else if (!this.menuOut || velocityX < MENU_RIGHT_MIN_BORDER) {
        this.enableScrolling();
        this.translateMenuX([this.options.MENU_WIDTH + MENU_VELOCITY_OFFSET, rightPos], '200');
        this.hideSidenavOverlay();
        this.$dragTarget.css({
          width: '10px',
          right: 0,
          left: ''
        });
      }
    }

    translateMenuX(fromTo, duration) {
      this.$menu.velocity({
        translateX: fromTo
      }, {
        duration: typeof duration === 'string' ? Number(duration) : duration,
        queue: false,
        easing: 'easeOutQuad'
      });
    }

    hideSidenavOverlay() {
      this.$sidenavOverlay.velocity({
        opacity: 0
      }, {
        duration: 200,
        queue: false,
        easing: 'easeOutQuad',

        complete() {
          $(this).remove();
        }

      });
      this.$sidenavOverlay = $();
    }

    showSidenavOverlay() {
      this.$sidenavOverlay.velocity({
        opacity: 1
      }, {
        duration: 50,
        queue: false,
        easing: 'easeOutQuad'
      });
    }

    enableScrolling() {
      this.$body.css({
        overflow: '',
        width: ''
      });
    }

    openOnClick() {
      var _this3 = this;

      this.$element.on('click', function (e) {
        e.preventDefault();

        if (_this3.menuOut === true) {
          _this3.menuOut = false;

          _this3.removeMenu();
        } else {
          _this3.$sidenavOverlay = $('<div id="sidenav-overlay"></div>');

          _this3.$body.append(_this3.$sidenavOverlay);

          let translateX = [];

          if (_this3.options.edge === 'left') {
            translateX = [0, -1 * _this3.options.MENU_WIDTH];
          } else {
            translateX = [0, _this3.options.MENU_WIDTH];
          }

          _this3.$menu.velocity({
            translateX
          }, {
            duration: 300,
            queue: false,
            easing: 'easeOutQuad'
          });

          _this3.$sidenavOverlay.on('click', function () {
            _this3.removeMenu();
          });
        }
      });
    }

    closeOnClick() {
      var _this4 = this;

      if (this.options.closeOnClick === true) {
        this.$menu.on('click', 'a:not(.collapsible-header)', function () {
          _this4.removeMenu();
        });
      }
    }

    setMenuTranslation() {
      var _this5 = this;

      if (this.options.edge === 'left') {
        this.$menu.css('transform', 'translateX(-100%)');
        this.$dragTarget.css({
          left: 0
        });
      } else {
        this.$menu.addClass('right-aligned').css('transform', 'translateX(100%)');
        this.$dragTarget.css({
          right: 0
        });
      }

      if (this.$menu.hasClass('fixed')) {
        if (window.innerWidth > SN_BREAKPOINT) {
          this.$menu.css('transform', 'translateX(0)');
        }

        $(window).resize(function () {
          if (window.innerWidth > SN_BREAKPOINT) {
            if (_this5.$sidenavOverlay.length) {
              _this5.removeMenu(true);
            } else {
              _this5.$menu.css('transform', 'translateX(0%)');
            }
          } else if (_this5.menuOut === false) {
            let xValue = _this5.options.edge === 'left' ? '-100' : '100';

            _this5.$menu.css('transform', `translateX(${xValue}%)`);
          }
        });
      }
    }

    setMenuWidth() {
      let $sidenavBg = $(`#${this.$menu.attr('id')}`).find('> .sidenav-bg');

      if (this.options.MENU_WIDTH !== MENU_WIDTH) {
        this.$menu.css('width', this.options.MENU_WIDTH);
        $sidenavBg.css('width', this.options.MENU_WIDTH);
      }
    }

    assignOptions(newOptions) {
      return $.extend({}, this.defaults, newOptions);
    }

    removeMenu(restoreMenu) {
      var _this6 = this;

      this.$body.css({
        overflow: '',
        width: ''
      });
      this.$menu.velocity({
        translateX: this.options.edge === 'left' ? '-100%' : '100%'
      }, {
        duration: 200,
        queue: false,
        easing: 'easeOutCubic',
        complete: function complete() {
          if (restoreMenu === true) {
            _this6.$menu.removeAttr('style');

            _this6.$menu.css('width', _this6.options.MENU_WIDTH);
          }
        }
      });
      this.hideSidenavOverlay();
    }

    show() {
      this.trigger('click');
    }

    hide() {
      this.$sidenavOverlay.trigger('click');
    }

  }

  $.fn.sideNav = function (options) {
    return this.each(function () {
      new SideNav($(this), options);
    });
  };
})(jQuery);