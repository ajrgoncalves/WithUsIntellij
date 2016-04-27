$ ->
  $.get "/users", (users) ->
    $.each users, (index, user) ->
      $('#users').append ("<li>" + user.id + " " + user.name + " " + user.lastName +  " " + user.age + " " + user.password + " " + user.email + "</li>")
