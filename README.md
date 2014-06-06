cc_adhoc
========

Ferramentas utilizadas
--------
- CORE - emulador de rede

Dificuldades e soluções adoptadas:
--------
- obtenção do nome do computador local -> ao arrancar a instância é solicitado um nome para qualificar essa instância. Contras: permite nomes duplicados.
- multicast enviado apenas por uma interface de rede -> obtém-se uma lista com todas as interfaces de rede e envia-se um multicast para cada interface que não seja do tipo loopback.
- peers repetidos -> se dois vizinhos devolverem um terceiro vizinho que ambos conhecem mas atingem com diferente número de saltos, é escolhido o vizinho que chega a esse peer com menos saltos. (Considera-se substituir por uma medida de tempo).
- os peers conhecidos dependem da ordem de arranque dos vários pontos -> o broadcast deverá ser enviado de x em x tempo para garantir uma estabilização da lista de peers assim como uma remoção de peers que eventualmente deixem de existir na rede.


Dúvidas
-------
-timeout: neste momento existe um timeout para o comando find, contudo esse mesmo timeout existirá no nó seguinte. Obviamente o timeout do pedido original irá ser atingido antes dos outros nós. Decisão tomada: quando o timeout é atingido é devolvida uma mensagem de timeout, contudo se entretando receber uma resposta positiva ao find, esta irá ser registada na mesma nos peers.
-é necessário guardar as mensagens enviadas. As mensagens enviadas devem ser enviadas todas através do mesmo socket?

Message structure:

HELLO:
HELLO|MACHINENAME|REQUESTORREPLY|peerlist|

ROUTEREQUEST:
ROUTE_REQUEST|MACHINE|DESTINATION|PEER2FIND|JUMPS|PEERS_PATHOFRETURN

ROUTEREPLY:
ROUTE_REPLY|MACHINE|DESTINATION|PEER2FIND|ANSWER|PEERS_PATHOFRETURN

PEERS_PATHOFRETURN:Lista de peers separada por tabs

Seria aconselhával adicionar um id para controlo do fluxo das mensagens? Adicionar id.

:
-Falta redirecionar as mensagens quando o peer não seja o destinatário das mensagens.

Concentrar envio e receção de mensagens no mesmo socket.
Multicast RouteRequest
Hello de x em x tempo.

No core se se mover um no as ligaçoes quebram-se. É porreiro para testar falhas de ligação.


