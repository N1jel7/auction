Проект "Аукцион". Реализовано 4 сущности "Lot", "Token", "Trader", "User". У сущностей присутвует связью многие ко многим. Реализована валидация данных и exception handler который в случае ошибки выдает тип ошибки и ее причину. Так же сделано разграничение доступа, для того что бы пользоваться приложением необходимо зарегистрироваться и в последующих запросах предоставлять токен (JWT Token).

Authentification Controller endpoints:

@PostMapping("api/v1/lots/login") - войти в сервис

@PostMapping("api/v1/lots/register") - регистрация в сервисе

@PostMapping("api/v1/lots/refresh_token") - обновить токен с помощью refresh_token


LotsRestController:


@GetMapping("api/v1/lots/active/page/{pageNumber}/{pageSize}") - выдает определенные страницы с активными лотами

@GetMapping("api/v1/lots/sold/page/{pageNumber}/{pageSize}") - выдает определенные страницы с проданными лотами

@GetMapping("api/v1/lots/active/range/{min}/{max}") - выдает список активных лотов с ценой в диапазоне min-max

@GetMapping("api/v1/lots/sold/range/{min}/{max}") - выдает список проданных лотов с ценой в диапазоне min-max

@GetMapping("api/v1/lots/name/contains/{containing}") - выдает список лотов в которых в имени содержится строка {containing}

@GetMapping("api/v1/lots/type/{type}") - ищет лоты с заданным типом

@GetMapping("api/v1/lots/{id}") - выдает лот по id

@PostMapping("api/v1/lots") - позволяет создать новый лот

@PatchMapping("api/v1/lots/{id}") - позволяет обновить информацию о лоте по id

@DeleteMapping("api/v1/lots/{id}") - позволяет удалить лот по id


TraderRestController endpoints:

@GetMapping("api/v1/traders/page/{pageNumber}/{pageSize}") - выдает определенную страницу с трейдерами

@PostMapping("api/v1/traders") - позволяет создать нового трейдера

@PatchMapping("api/v1/traders/{id}") - позволяет посмотреть информацию о трейдере по id

@DeleteMapping("api/v1/traders/{id}") - позволяет удалить трейдера по id
