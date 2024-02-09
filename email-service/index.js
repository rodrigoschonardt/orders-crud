const nodemailer = require( 'nodemailer' );
const amqp = require( 'amqplib/callback_api' );

const transporter = nodemailer.createTransport( {
  port: 465,
  host: "smtp.gmail.com",
     auth: {
          user: process.env.EMAIL,
          pass: process.env.PASSWORD,
       },
  secure: true,
} );

amqp.connect( `amqp://${process.env.RABBITMQ_HOST ?? localhost}`, ( connError, connection ) => { 
  if ( connError ) {
    throw connError
  }

  connection.createChannel( ( channelError, channel ) => {
    if ( channelError ) {
      throw channelError
    }

    const queue = 'order-email'
  
    channel.assertQueue( queue, {
      durable: false
    } )

    channel.consume( queue, ( msg ) => {
      const data = JSON.parse( Buffer.from( JSON.parse( JSON.stringify( msg.content ).toString() ).data ).toString() )

      var body = `<h1>${data.name}</h1>` +
                 `<h2>${data.info}</h2>` +
                 `<h2>Items:</h2>` +
                 `<ul>`

      data.items.forEach( element => {
          body += `<li>${element.name}</li>`
      } );

      body += `</ul>`

      const mailData = {
        from: process.env.EMAIL,
          to: data.email,  
          subject: 'New Order',
          text: '',
          html: body
      };

      if ( process.env.PASSWORD && process.env.EMAIL )
      {
        transporter.sendMail( mailData, ( mailError ) => {
          if ( mailError ) {
            throw mailError
          }
        } )
      }

      channel.ack(msg)
    } )
  } )
} )