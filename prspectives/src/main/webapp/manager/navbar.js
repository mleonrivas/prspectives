if (! MODELHANDLER) {
    var MODELHANDLER = new ModelHandler();
}

var NavBar = {};

(function($) {
    NavBar = {
        namesList: [],
        mapNamesId: {},
        onLogin: $.Callbacks(),
        _isLogged: false,
        _baseUrl: "service",

        init: function ()  {
            if (typeof ORYX != "undefined")
                this._baseUrl = "../service";

            $("#editModelNavbar").typeahead({
                source: NavBar.modelsSourceLoader,
                items: 6
            });

            $("#editModelFormNavbar").submit(function() {
                location.href = NavBar.mapNamesId[$("#editModelNavbar").val()];
                return false;
            });

            NavBar.setCurrentUser();
        },

        modelsSourceLoader: function (q, callback) {
            MODELHANDLER.loadModelsList().done(function(processes) {
                NavBar.namesList = [];
                NavBar.mapNamesId = {};
                $(processes).each(function() {
                    if (typeof this.name != "undefined" && typeof this.editor != "undefined" && this.editor != null) {
                        var name = this.name + " ("+this.modelId+")";
                        NavBar.namesList.push(name);
                        NavBar.mapNamesId[name] = this.editor;
                    }
                });

                callback(NavBar.namesList);
            });
        },

        currentUser: function () {
            return $.ajax({
                type: "GET",
                url: NavBar._baseUrl + "/user"
            });
        },

        logout: function () {
            return $.ajax({
                type: "POST",
                url: NavBar._baseUrl + "/user/logout"
            });
        },

        setCurrentUser: function() {
            var elem = $("#username");
            this.currentUser().done(function(data) {
                 NavBar._isLogged = true;
                 elem.html(data);
                 var logout = $('<a id="logout" href="#"><i class="icon-off"></i> Logout</a>').click(function() {
                      NavBar.logout().done(function () {
                          NavBar.setCurrentUser();
                      });
                  });
                var menu = $('<ul class="dropdown-menu"></ul>').append($("<li></li>").append(logout));
                NavBar.loggedOutContent = $("#login .dropdown-menu").replaceWith(menu);
                NavBar.onLogin.fire(NavBar._isLogged);

             }).fail(function (data) {
                NavBar._isLogged = false;
                 elem.html("Log in");
                 if (typeof NavBar.loggedOutContent != "undefined")
                    $("#login .dropdown-menu").replaceWith(NavBar.loggedOutContent);

                 NavBar.onLogin.fire(NavBar._isLogged);
             });
        },

        isLogged: function() {
            return this._isLogged;
        }


    };
})(jQuery);