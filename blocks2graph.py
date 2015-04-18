import json
import collections
import sys

with open(sys.argv[1]) as f:
    data = json.load(f)

def pt(format_string, *args, **kwargs):
    print('        ' + format_string.format(*args, **kwargs))

pt('Simulation sim = new Simulation({});', len(data['nodes']))

link_ixes = collections.defaultdict(lambda: 0)

nodes = data['nodes']

idmap = {node['id']: new_id for new_id, node in enumerate(nodes)}

x_min = min(node['x'] for node in nodes)
x_max = max(node['x'] for node in nodes)
y_min = min(node['y'] for node in nodes)
y_max = max(node['y'] for node in nodes)

xoff = 512 - (x_max + x_min) / 2
yoff = 320 - (y_max + y_min) / 2

def round(x, multiples_of=5):
    return int((x + multiples_of/2) / multiples_of) * multiples_of

for node in nodes:
    pt('sim.setNodeX({id}, {x}f);', id=idmap[node['id']], x=round(node['x'] + xoff))
    pt('sim.setNodeY({id}, {y}f);', id=idmap[node['id']], y=round(node['y'] + yoff))
    if node['title'] == 'SLIME':
        pt('sim.setSide({id}, Side.SLIMES);', id=idmap[node['id']])
    elif node['title'] == 'VIRUS':
        pt('sim.setSide({id}, Side.VIRUSES);', id=idmap[node['id']])
for link in data['edges']:
    src = link['source']
    dst = link['target']
    src_ix = link_ixes[src]
    link_ixes[src] += 1
    dst_ix = link_ixes[dst]
    link_ixes[dst] += 1
    pt('sim.setLink({src}, {ix}, {dst});', src=idmap[src], ix=src_ix, dst=idmap[dst])
    pt('sim.setLink({src}, {ix}, {dst});', src=idmap[dst], ix=dst_ix, dst=idmap[src])
pt('sim.tickSimulation();')
pt('return sim;')
