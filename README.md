BatalhaNaval
============

Redirecionamento do emulador A (a porta deste emulador é 5554)

No CMD (modo admin)

telnet localhost 5554

redir add tcp:5050:8090

redirecionamento do emulador B  (a porta deste emulador é 5556)

telnet localhost 5556

redir add tcp:5051:8090


Dessa forma para que A se conecte a B ->> 10.0.2.2:5050
E para que B se conecte a A ->> 10.0.2.2:5051
