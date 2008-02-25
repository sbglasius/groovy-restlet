package org.lpny.gr.examples.tutorials
import org.restlet.resource.*

builder.component{
    current.servers.add(protocol.HTTP, 8182)
    
    application(uri:""){
        router{
            resource("/users/{user}",
                    init:{ctx, req, resp, self->                            
                        self.getVariants().add(new Variant(mediaType.TEXT_PLAIN))
                    },
                    represent:{variant->
                        println "To return represent"
                        return new StringRepresentation(
                                "Account of user \"${request.attributes.get('user')}");
                    })
        }
    }
}.start()

def client = builder.client(protocol:protocol.HTTP)

def resp = client.get("http://localhost:8182/users/test")
assert resp.status == status.SUCCESS_OK