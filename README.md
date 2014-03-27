cc_adhoc
========

Ferramentas utilizadas
========
- CORE - emulador de rede

Dificuldades e soluções adoptadas:
========
- obtenção do nome do computador local -> ao arrancar a instância é solicitado um nome para qualificar essa instância. Contras: permite nomes duplicados.
- multicast enviado apenas por uma interface de rede -> obtém-se uma lista com todas as interfaces de rede e envia-se um multicast para cada interface que não seja do tipo loopback.
- peers repetidos -> se dois vizinhos devolverem um terceiro vizinho que ambos conhecem mas atingem com diferente número de saltos, é escolhido o vizinho que chega a esse peer com menos saltos. (Considera-se substituir por uma medida de tempo).
- os peers conhecidos dependem da ordem de arranque dos vários pontos -> o broadcast deverá ser enviado de x em x tempo para garantir uma estabilização da lista de peers assim como uma remoção de peers que eventualmente deixem de existir na rede.

